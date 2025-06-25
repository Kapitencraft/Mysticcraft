package net.kapitencraft.mysticcraft.item.material.containable;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.containable.ContainableCapability;
import net.kapitencraft.mysticcraft.item.capability.containable.ContainableCapabilityProvider;
import net.kapitencraft.mysticcraft.item.capability.containable.IContainable;
import net.kapitencraft.mysticcraft.registry.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ContainableItem<T extends Item, C extends IContainable<T>> extends Item implements ExtendedItem {

    private final int stackSize;

    public ContainableItem(Properties p_41383_, int stackSize) {
        super(p_41383_.stacksTo(1));
        this.stackSize = stackSize;
    }


    public int getDefaultStackSize() {
        return stackSize;
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        List<ItemStack> contents = getContents(itemStack);
        if (contents != null) contents.forEach((holder) -> list.add(((MutableComponent) holder.getHoverName()).withStyle(MiscHelper.getItemRarity(holder.getItem()).getStyleModifier()).append(": " + holder.getCount())));
    }

    public int getUsedCapacity(ItemStack stack) {
        List<ItemStack> list = getContents(stack);
        return list == null ? 0 : MathHelper.count(list.stream().map(ItemStack::getCount).toList());
    }

    public int getRemainingCapacity(ItemStack stack) {
        return getCapacity(stack) - getUsedCapacity(stack);
    }


    public int getCapacity(ItemStack stack) {
        return (int) (stackSize * (1 + stack.getEnchantmentLevel(ModEnchantments.CAPACITY.get()) * 0.25) * 64);
    }

    public int getStackCapacity(ItemStack stack) {
        return getCapacity(stack) / 64;
    }


    public static @Nullable List<ItemStack> getContents(@NotNull ItemStack stack) {
        return CapabilityHelper.mapCapability(stack, CapabilityHelper.QUIVER, ContainableCapability::getContent);
    }

    public ItemStack getItem(@NotNull ItemStack stack, Item item) {
        if (canApply(item)) {
            return CapabilityHelper.mapCapability(stack, CapabilityHelper.QUIVER, quiverCapability -> quiverCapability.getHolder(item));
        }
        return ItemStack.EMPTY;
    }

    //Functionality

    public boolean overrideStackedOnOther(@NotNull ItemStack stack, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player) {
        if (action != ClickAction.SECONDARY) {
            return false;
        } else {
            List<ItemStack> contents = getContents(stack);
            ItemStack itemstack = slot.getItem();
            if (itemstack.isEmpty() && contents != null && !contents.isEmpty()) {
                ItemStack returned = remove(contents.get(0), 64, stack);
                slot.safeInsert(returned);
                this.playDropContentsSound(player);
            } else if (canApply(itemstack.getItem()) && contents != null) {
                int freeSpace = getRemainingCapacity(stack);
                ItemStack toPut = slot.safeTake(itemstack.getCount(), freeSpace, player);
                if (toPut == ItemStack.EMPTY) return true;
                ItemStack holder = getHolder(toPut, contents);
                holder.grow(toPut.getCount());
                playInsertSound(player);
            }
            return true;
        }
    }

    public boolean overrideOtherStackedOnMe(@NotNull ItemStack stack, @NotNull ItemStack stack1, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player, @NotNull SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            List<ItemStack> list = getContents(stack);
            if (list == null) return true;
            if (stack1.isEmpty() && !list.isEmpty()) {
                remove(list.get(0), 1, stack);
            } else if (canApply(stack1.getItem())) {
                int freeValue = getRemainingCapacity(stack);
                ItemStack holder = getHolder(stack1, list);
                int toGrow = Math.min(freeValue, stack1.getCount());
                holder.grow(toGrow);
                if (toGrow > 0) {
                    playInsertSound(player);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    protected boolean canApply(Item item) {
        try {
            T t = (T) item;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public ItemStack getHolder(ItemStack value, List<ItemStack> contents) {
        if (contents == null) return ItemStack.EMPTY;
        for (ItemStack holder : contents) {
            if (ItemStack.isSameItemSameTags(holder, value)) {
                return holder;
            }
        }
        ItemStack holder = new ItemStack(value.getItem());
        holder.setTag(value.getTag());
        contents.add(holder);
        return holder;
    }

    public ItemStack remove(@Nullable ItemStack item, int maxAmount, ItemStack source) {
        List<ItemStack> contents = getContents(source);
        if (item == null || contents == null) return ItemStack.EMPTY;
        ItemStack holder = getHolder(item, contents);
        ItemStack temp = holder.split(maxAmount);
        if (holder.isEmpty()) contents.remove(holder);
        return temp;
    }

    public ItemStack put(ItemStack stack, ItemStack source) {
        ItemStack holder = getHolder(stack, getContents(source));
        if (holder.isEmpty()) return ItemStack.EMPTY;
        int free = getRemainingCapacity(source);
        if (free < stack.getCount()) {
            holder.grow(free);
            stack.shrink(free);
            return stack;
        } else {
            holder.grow(stack.getCount());
            return ItemStack.EMPTY;
        }
    }


    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    public abstract ContainableCapabilityProvider<T, C> makeCapabilityProvider();
}