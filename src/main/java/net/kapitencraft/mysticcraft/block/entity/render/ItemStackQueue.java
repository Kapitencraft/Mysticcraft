package net.kapitencraft.mysticcraft.block.entity.render;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemStackQueue {
    private final List<ItemStack> itemStacks = new ArrayList<>();
    private final List<ItemStack> queue = new ArrayList<>();
    private final List<ItemStack> removeQueue = new ArrayList<>();
    private boolean toQueue = false;

    public void add(ItemStack stack) {
        if (toQueue) {
            queue.add(stack);
        } else {
            itemStacks.add(stack);
        }
    }

    public boolean contains(ItemStack stack) {
        return this.itemStacks.contains(stack) || this.queue.contains(stack);
    }

    public boolean isEmpty() {
        return this.itemStacks.isEmpty() && this.queue.isEmpty();
    }

    public boolean remove(ItemStack stack) {
        return this.removeQueue.add(stack);
    }

    public ItemStackQueue() {}

    private ItemStackQueue(List<ItemStack> list) {
        this.itemStacks.addAll(list);
    }

    public void forEach(Consumer<ItemStack> consumer) {
        this.toQueue = true;
        this.itemStacks.forEach(consumer);
        this.toQueue = false;
        this.itemStacks.removeAll(removeQueue);
        this.itemStacks.addAll(queue);
        this.queue.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ItemStack stack : this.itemStacks) {
            builder.append(stack.toString());
        }
        StringBuilder queueBuilder = new StringBuilder();
        for (ItemStack stack : this.queue) {
            queueBuilder.append(stack.toString());
        }

        return "ItemStackQueue{Stacks: " + builder + ", Queue" + queueBuilder;
    }

    public static ItemStackQueue of(List<ItemStack> list) {
        return new ItemStackQueue(list);
    }
}