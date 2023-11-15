package net.kapitencraft.mysticcraft.item.data.dungeon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface IPrestigeAbleItem {

    ItemStack prestige(ItemStack stack);

    boolean mayPrestige(ItemStack stack, boolean fromCommand);


    Map<Item, Integer> getMatCost(ItemStack stack);
}