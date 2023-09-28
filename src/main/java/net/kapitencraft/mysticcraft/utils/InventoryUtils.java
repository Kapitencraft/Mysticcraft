package net.kapitencraft.mysticcraft.utils;

import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public static List<ItemStack> getByFilter(Player player, Predicate<ItemStack> predicate) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (predicate.test(stack)) itemStacks.add(stack);
        }
        return itemStacks;
    }

    public static boolean hasSetInInventory(Player player,  ArmorMaterial material) {
        boolean helmetFound = false;
        boolean chestplateFound = false;
        boolean leggingsFound = false;
        boolean bootsFound = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() == material) {
                    switch (armorItem.getSlot()) {
                        case LEGS -> leggingsFound = true;
                        case HEAD -> helmetFound = true;
                        case CHEST -> chestplateFound = true;
                        case FEET -> bootsFound = true;
                    }
                }
            }
        }
        return leggingsFound && helmetFound && chestplateFound && bootsFound;
    }

    public static boolean hasSetInInventory(Player player, TieredArmorItem.ArmorTier armorTier) {
        boolean helmetFound = false;
        boolean chestplateFound = false;
        boolean leggingsFound = false;
        boolean bootsFound = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof TieredArmorItem armorItem) {
                if (TieredArmorItem.getTier(stack) == armorTier) {
                    switch (armorItem.getSlot()) {
                        case LEGS -> leggingsFound = true;
                        case HEAD -> helmetFound = true;
                        case CHEST -> chestplateFound = true;
                        case FEET -> bootsFound = true;
                    }
                }
            }
        }
        return leggingsFound && helmetFound && chestplateFound && bootsFound;
    }
}