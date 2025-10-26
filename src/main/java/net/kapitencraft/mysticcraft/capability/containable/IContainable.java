package net.kapitencraft.mysticcraft.capability.containable;

import net.minecraft.world.item.Item;

public interface IContainable<T extends Item> {
    boolean checkCanInsert(Item item);
    int insert(T item, int amount);
    int remove(T item, int amount);
    int amount(T item);
    void setMaxAmount(int amount);
    int getMaxAmount();
}