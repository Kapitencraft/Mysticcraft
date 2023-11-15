package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.item.ITieredItem;
import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Reference;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class InventoryHelper {
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

    private static Predicate<ItemStack> byItem(Item item) {
        return stack -> stack.getItem() == item;
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

    public static boolean hasSetInInventory(Player player, ITieredItem.ItemTier armorTier) {
        boolean helmetFound = false;
        boolean chestplateFound = false;
        boolean leggingsFound = false;
        boolean bootsFound = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof TieredArmorItem armorItem) {
                if (ITieredItem.getTier(stack) == armorTier) {
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

    @SuppressWarnings("ALL")
    public static boolean hasInInventory(Map<Item, Integer> content, Player player) {
        for (Map.Entry<Item, Integer> entry : content.entrySet()) {
            Reference<Integer> reference = Reference.of(entry.getValue());
            List<ItemStack> list = getByFilter(player, byItem(entry.getKey()));
            list.forEach(stack -> MathHelper.add(reference::getValue, reference::setValue, -stack.getCount()));
            if (reference.getValue() > 0) {
                return false;
            }
        }
        return true;
    }
}