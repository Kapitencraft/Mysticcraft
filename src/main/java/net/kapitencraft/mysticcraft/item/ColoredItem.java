package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public interface ColoredItem {

    static int getColor(ItemStack stack) {
        return DyedItemColor.getOrDefault(stack, 10511680) | 0xFF000000;
        }
}
