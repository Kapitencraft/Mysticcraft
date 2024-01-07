package net.kapitencraft.mysticcraft.item.data.dungeon;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.data.essence.IEssenceData;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IPrestigeAbleItem {
    static ItemStack essence(EssenceType type, int amount) {
        ItemStack essenceStack = new ItemStack(ModItems.ESSENCE.get(), amount);
        IEssenceData.apply(essenceStack, type);
        return essenceStack;
    }

    ItemStack prestige(ItemStack stack);

    boolean mayPrestige(ItemStack stack, boolean fromCommand);


    List<ItemStack> getMatCost(ItemStack stack);
}