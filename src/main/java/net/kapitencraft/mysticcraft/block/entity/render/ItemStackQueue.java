package net.kapitencraft.mysticcraft.block.entity.render;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemStackQueue {
    private final List<ItemStack> itemStacks = new ArrayList<>();
    private final List<ItemStack> queue = new ArrayList<>();
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
        if (this.toQueue) return false;
        return this.itemStacks.remove(stack);
    }

    public ItemStackQueue() {}

    private ItemStackQueue(List<ItemStack> list) {
        this.itemStacks.addAll(list);
    }

    public void forEach(Consumer<ItemStack> consumer) {
        this.toQueue = true;
        this.itemStacks.forEach(consumer);
        this.toQueue = false;
        this.itemStacks.addAll(queue);
        this.queue.clear();
    }

    public static ItemStackQueue of(List<ItemStack> list) {
        return new ItemStackQueue(list);
    }
}