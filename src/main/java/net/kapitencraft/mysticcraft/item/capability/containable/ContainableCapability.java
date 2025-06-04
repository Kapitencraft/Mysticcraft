package net.kapitencraft.mysticcraft.item.capability.containable;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ContainableCapability<T extends Item> implements IContainable<T> {

    private int maxAmount;
    private final List<ItemStack> content = new ArrayList<>();

    public List<ItemStack> getContent() {
        return content;
    }

    @Override
    public void copyFrom(List<ItemStack> itemStacks) {
        this.content.clear();
        this.content.addAll(itemStacks);
    }

    @Override
    public List<ItemStack> getData() {
        return ImmutableList.copyOf(this.content);
    }

    @Override
    public int insert(T item, int amount) {
        ItemStack holder = getHolder(item);
        if (holder != null) {
            int growth = maxAmount - holder.getCount();
            holder.grow(growth);
            return amount - growth;
        }
        return amount;
    }

    public ItemStack getHolder(Item item) {
        Optional<ItemStack> optional = this.content.stream().filter(stack -> stack.is(item)).findAny();
        if (optional.isPresent()) return optional.get();
        if (checkCanInsert(item)) {
            ItemStack holder = new ItemStack(item);
            this.content.add(holder);
            return holder;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int remove(Item item, int amount) {
        ItemStack holder = getHolder(item);
        if (holder != null) {
            holder.shrink(amount);
            amount = holder.getCount();
            if (amount < 0) content.remove(holder);
            return amount;
        }
        return amount;
    }

    @Override
    public int amount(Item item) {
        ItemStack holder = getHolder(item);
        return holder == null ? 0 : holder.getCount();
    }

    @Override
    public void setMaxAmount(int amount) {
        this.maxAmount = amount;
    }

    @Override
    public int getMaxAmount() {
        return this.maxAmount;
    }
}
