package net.kapitencraft.mysticcraft.item.material.containable;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.data_gen.registry.ModEnchantments;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;
import java.util.function.Function;

public class WalletItem extends Item {
    private final int maxBalance;

    public WalletItem() {
        this(1024);
    }

    protected WalletItem(int maxBalance) {
        super(MiscHelper.rarity(Rarity.UNCOMMON).component(ModDataComponentTypes.WALLET_BALANCE, 0));
        this.maxBalance = maxBalance;
    }

    public static int getMaxBalance(ItemStack stack, Function<ResourceKey<Enchantment>, Holder<Enchantment>> holderGetter) {
        return ((WalletItem) stack.getItem()).maxBalance * (1 + stack.getEnchantmentLevel(holderGetter.apply(ModEnchantments.CAPACITY)));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        } else {
            Integer balance = stack.get(ModDataComponentTypes.WALLET_BALANCE);
            if (balance == null) return false;
            ItemStack itemStack = slot.getItem();
            if (itemStack.isEmpty()) {
                if (balance > 1) {
                    playRemoveOneSound(player);
                    ItemStack stack1 = new ItemStack(Items.EMERALD);
                    slot.safeInsert(stack1);
                    balance--;
                }
            } else if (itemStack.is(Items.EMERALD)) {
                int remainingCapacity = getMaxBalance(stack, player.registryAccess()::holderOrThrow) - balance;
                int accepted = Math.min(remainingCapacity, itemStack.getCount());
                if (accepted > 0) {
                    playInsertSound(player);
                    balance += accepted;
                    itemStack.shrink(accepted);
                }
            }
            stack.set(ModDataComponentTypes.WALLET_BALANCE, balance);
            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY || !slot.allowModification(player)) return false;
        Integer balance = stack.get(ModDataComponentTypes.WALLET_BALANCE);

        if (balance == null) return false;
        if (other.isEmpty()) {
            if (balance > 1) {
                this.playRemoveOneSound(player);
                access.set(new ItemStack(Items.EMERALD));
            }
        } else if (other.is(Items.EMERALD)) {
            int remainingCapacity = getMaxBalance(stack, player.registryAccess()::holderOrThrow) - balance;
            int accepted = Math.min(remainingCapacity, other.getCount());
            if (accepted > 0) {
                playInsertSound(player);
                balance += accepted;
                other.shrink(accepted);
            }
        }
        stack.set(ModDataComponentTypes.WALLET_BALANCE, balance);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (context.registries() != null)
            tooltipComponents.add(Component.translatable("wallet.balance", stack.get(ModDataComponentTypes.WALLET_BALANCE), getMaxBalance(stack, context.registries()::holderOrThrow)));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}
