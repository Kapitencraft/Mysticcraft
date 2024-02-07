package net.kapitencraft.mysticcraft.item.capability.dungeon;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IReAnUpgradeable {

    ItemStack upgrade(ItemStack stack);

    boolean mayUpgrade(ItemStack stack);

    List<ItemStack> getMatCost(ItemStack stack);
}
