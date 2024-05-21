package net.kapitencraft.mysticcraft.item.capability.containable;

import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IContainable<T extends Item> extends ICapability<ContainableCapability<T>> {
    boolean checkCanInsert(Item item);
    int insert(T item, int amount);
    int remove(T item, int amount);
    int amount(T item);
    void setMaxAmount(int amount);
    int getMaxAmount();
}