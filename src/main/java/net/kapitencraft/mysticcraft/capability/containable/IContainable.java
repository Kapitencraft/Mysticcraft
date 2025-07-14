package net.kapitencraft.mysticcraft.capability.containable;

import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.List;

@AutoRegisterCapability
public interface IContainable<T extends Item> extends AbstractCapability<List<ItemStack>> {
    boolean checkCanInsert(Item item);
    int insert(T item, int amount);
    int remove(T item, int amount);
    int amount(T item);
    void setMaxAmount(int amount);
    int getMaxAmount();
}