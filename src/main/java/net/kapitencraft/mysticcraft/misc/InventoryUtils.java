package net.kapitencraft.mysticcraft.misc;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static boolean hasPlayerStackInInventory(Player player, Item item) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack current = inventory.getItem(i);
            if (!current.isEmpty() && current.sameItem(new ItemStack(item))) {
                return true;
            }
        }
        return false;
    }

    public static int getFirstInventoryIndex(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return i;
            }
        }

        return -1;
    }

    public static ItemStack getFirstStackInventoryIndex(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return currentStack;
            }
        }
        return ItemStack.EMPTY;
    }
}
