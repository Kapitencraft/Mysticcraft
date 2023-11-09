package net.kapitencraft.mysticcraft.item.persistant;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class SaveableContainer<T extends ItemStackSaveable> {

    private final HashMap<ItemStack, T> content = new HashMap<>();

    public SaveableContainer() {
    }

    public boolean contains(ItemStack stack) {
        return content.containsKey(stack);
    }

    public void add(ItemStack stack, T content) {
        this.content.put(stack, content);
    }

    public T get(ItemStack stack) {
        return content.get(stack);
    }
}
