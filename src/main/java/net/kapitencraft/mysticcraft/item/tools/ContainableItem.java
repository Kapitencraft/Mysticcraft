package net.kapitencraft.mysticcraft.item.tools;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainableItem<T extends Item> extends Item {
    private final int stackSize;
    public ContainableItem(Properties p_41383_, int stackSize) {
        super(p_41383_.stacksTo(1));
        this.stackSize = stackSize;
    }

    public int getUsedCapacity(ItemStack stack) {
        return MathUtils.count(getContents(stack).stream().map(ContainableHolder::getAmount).toList());
    }


    public int getCapacity(ItemStack stack) {
        return (int) (stackSize * (1 + stack.getEnchantmentLevel(ModEnchantments.CAPACITY.get()) * 0.25) * 64);
    }

    public int getStackCapacity(ItemStack stack) {
        return getCapacity(stack) / 64;
    }


    public List<ContainableHolder<T>> getContents(@NotNull ItemStack stack) {
        CompoundTag tag = stack.getTagElement("Contents");
        List<ContainableHolder<T>> list = new ArrayList<>();
        if (tag != null) {
            int i = 0;
            while (tag.contains("Value " + i, 10)) {
                ContainableHolder<T> fromTag = ContainableHolder.fromTag(tag.getCompound("Value " + i));
                i++;
                if (fromTag != null && fromTag.amount > 0) {
                    list.add(fromTag);
                }
            }
        }
        return list;
    }

    public CompoundTag saveContents(ItemStack stack, List<ContainableHolder<T>> toSave) {
        CompoundTag tag = new CompoundTag();
        int i = 0;
        for (ContainableHolder<T> holder : toSave) {
            tag.put("Value " + i, holder.toTag());
            i++;
        }
        stack.addTagElement("Contents", tag);
        return tag;
    }

    //Functionality

    public boolean overrideStackedOnOther(@NotNull ItemStack stack, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player) {
        if (action != ClickAction.SECONDARY) {
            return false;
        } else {
            List<ContainableHolder<T>> contents = getContents(stack);
            ItemStack itemstack = slot.getItem();
            if (itemstack.isEmpty() && !contents.isEmpty()) {
                ItemStack returned = remove(contents.get(0).getDefaultStack(), Screen.hasControlDown() ? 64 : 1, contents);
                slot.safeInsert(returned);
                this.playDropContentsSound(player);
            } else if (canApply(itemstack.getItem())) {
                int freeSpace = getCapacity(stack) - getUsedCapacity(stack);
                ItemStack toPut = slot.safeTake(itemstack.getCount(), freeSpace, player);
                ContainableHolder<T> holder = getHolder(toPut, contents);
                holder.grow(toPut.getCount());
                if (toPut != ItemStack.EMPTY) {
                    playInsertSound(player);
                }
            }

            saveContents(stack, contents);
            return true;
        }
    }

    public boolean overrideOtherStackedOnMe(@NotNull ItemStack stack, @NotNull ItemStack stack1, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player, @NotNull SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            List<ContainableHolder<T>> list = getContents(stack);
            if (stack1.isEmpty()) {
                ItemStack first = list.get(0).getDefaultStack();
                remove(first, 1, list);
            } else if (canApply(stack1.getItem())) {
                int freeValue = getCapacity(stack) - getUsedCapacity(stack);
                ContainableHolder<T> holder = getHolder(stack1, list);
                int toGrow = Math.min(freeValue, stack1.getCount());
                holder.grow(toGrow);
                if (toGrow > 0) {
                    playInsertSound(player);
                }
            }

            saveContents(stack, list);
            return true;
        } else {
            return false;
        }
    }

    public boolean canApply(Item item) {
        try {
            T t = (T) item;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public ContainableHolder<T> getHolder(ItemStack value, List<ContainableHolder<T>> contents) {
        for (ContainableHolder<T> holder : contents)  {
            if (holder.item == value.getItem() && holder.tag.equals(value.getTag())) {
                return holder;
            }
        }
        ContainableHolder<T> holder = new ContainableHolder<>((T) value.getItem());
        holder.setTag(value.getTag());
        contents.add(holder);
        return holder;
    }

    private ItemStack remove(@Nullable ItemStack item, int maxAmount, List<ContainableHolder<T>> contents) {
        return item == null ? ItemStack.EMPTY : getHolder(item, contents).makeStack(maxAmount);
    }


    private void playInsertSound(Entity p_186352_) {
        p_186352_.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + p_186352_.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity p_186354_) {
        p_186354_.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + p_186354_.getLevel().getRandom().nextFloat() * 0.4F);
    }
}