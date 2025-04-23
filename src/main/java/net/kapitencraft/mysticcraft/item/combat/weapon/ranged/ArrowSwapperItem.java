package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ArrowSwapperItem extends Item {
    public ArrowSwapperItem() {
        super(MiscHelper.rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player p_41433_, @NotNull InteractionHand p_41434_) {

        return super.use(p_41432_, p_41433_, p_41434_);
    }


    private static void swapArrow(Player player, int change) {
        List<ItemStack> stacks = findProjectiles(player);
        ItemStack active = getActiveArrow(player);
        stacks.stream().filter(stack -> ItemStack.isSameItemSameTags(active, stack))
                .findFirst().ifPresent(stack -> {
                    int index = stacks.indexOf(stack);
                    if (index == -1) throw new IllegalStateException("Arrow Missing: " + stack);
                    index += change;
                    if (index < 0) {
                        index = stacks.size() - 1 + index;
                    }
                    if (index >= stacks.size()) {
                        index -= stacks.size();
                    }
                    setActiveArrow(player, stacks.get(index));
                });
    }

    private static List<ItemStack> findProjectiles(Player player) {
        return InventoryHelper.getByFilter(player, stack -> stack.getItem() instanceof QuiverItem)
                .stream().map(ContainableItem::getContents)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
    }

    private static ItemStack getActiveArrow(Player player) {
            return ItemStack.of(player.getPersistentData().getCompound("ActiveArrow"));
    }

    private static void setActiveArrow(Player player, ItemStack stack) {
        player.getPersistentData().put("ActiveArrow", stack.save(new CompoundTag()));
    }
}