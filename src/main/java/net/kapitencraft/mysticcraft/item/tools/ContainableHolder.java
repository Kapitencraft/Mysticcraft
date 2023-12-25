package net.kapitencraft.mysticcraft.item.tools;

import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ContainableHolder<T extends Item> {
    int amount = 0;
    final T item;
    CompoundTag tag;

    public ContainableHolder(T item) {
        this.item = item;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    public int getAmount() {
        return amount;
    }

    public CompoundTag getTag() {
        return tag == null ? new CompoundTag() : tag;
    }

    public T getItem() {
        return item;
    }

    public void grow(int amount) {
        this.amount += amount;
    }

    public static <T extends Item> ContainableHolder<T> fromTag(CompoundTag tag) {
        int amount = tag.getInt("Amount");
        T item = (T) TextHelper.getFromId(tag.getString("Item"));
        CompoundTag compoundTag = tag.getCompound("Info");
        if (item != Items.AIR) {
            ContainableHolder<T> holder = new ContainableHolder<>(item);
            holder.setTag(compoundTag);
            holder.setAmount(amount);
            return holder;
        }
        return null;
    }

    public CompoundTag toTag() {
        CompoundTag tag1 = new CompoundTag();
        tag1.putInt("Amount", this.amount);
        tag1.putString("Item", TextHelper.getTextId(this.item));
        tag1.put("Info", this.getTag());
        return tag1;
    }

    public ItemStack makeStack(int maxAmount) {
        ItemStack stack = getDefaultStack();
        stack.setCount(Math.min(this.amount, maxAmount));
        this.amount -= stack.getCount();
        return stack;
    }

    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this.item);
        stack.setTag(this.tag);
        return stack;
    }
}
