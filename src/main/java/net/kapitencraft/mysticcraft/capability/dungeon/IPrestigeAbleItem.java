package net.kapitencraft.mysticcraft.capability.dungeon;

import net.kapitencraft.mysticcraft.capability.essence.IEssenceData;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.item.ItemStack;

public interface IPrestigeAbleItem extends IReAnUpgradeable {
    static ItemStack essence(EssenceType type, int amount) {
        ItemStack essenceStack = new ItemStack(ModItems.ESSENCE.get(), amount);
        IEssenceData.apply(essenceStack, type);
        return essenceStack;
    }
}