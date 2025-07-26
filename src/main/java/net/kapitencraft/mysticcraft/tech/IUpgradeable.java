package net.kapitencraft.mysticcraft.tech;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public interface IUpgradeable {

    boolean canUpgrade(ItemStack upgradeModule);

    void upgrade(ItemStack stack);

    int upgradeSlots();

    ItemStackHandler getUpgrades();
}