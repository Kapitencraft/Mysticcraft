package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.item.ITieredItem;
import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InventoryHelper {
    public static boolean hasPlayerStackInInventory(Player player, Item item) {
        Inventory inventory = player.getInventory();
        return allInventory(inventory).stream()
                .anyMatch(stack -> !stack.isEmpty() && stack.sameItem(new ItemStack(item)));
    }

    public static void forInventory(Inventory inventory, Consumer<ItemStack> consumer) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            consumer.accept(inventory.getItem(i));
        }
    }

    public static List<ItemStack> allInventory(Inventory inventory) {
        List<ItemStack> list = new ArrayList<>();
        forInventory(inventory, list::add);
        return list;
    }

    public static boolean removeFromInventory(ItemStack stack, Player player) {
        Inventory inventory = player.getInventory();
        forInventory(inventory, stack1 -> {
            MiscHelper.ensureTags(stack, stack1);
            if (ItemStack.isSameItemSameTags(stack, stack1) && stack.getCount() > 0) {
                int size = Math.min(stack1.getCount(), stack.getCount());
                stack1.shrink(size);
                stack.shrink(size);
            }
        });
        return stack.getCount() <= 0;
    }

    public static int getFirstInventoryIndex(Item item, Player player) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.sameItem(new ItemStack(item))) {
                return i;
            }
        }
        return -1;
    }

    public static ItemStack getFirstStackInventoryIndex(Player player, Item item) {
        return allInventory(player.getInventory()).stream().filter(
                stack -> checkItem(stack, item)
        ).findFirst().orElse(ItemStack.EMPTY);
    }

    private static boolean checkItem(ItemStack stack, Item item) {
        return !stack.isEmpty() && stack.is(item);
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
        List<EquipmentSlot> slots = new ArrayList<>();
        MiscHelper.instanceStream(allInventory(player.getInventory()).stream().map(ItemStack::getItem).filter(
                item -> item instanceof ArmorItem armorItem && armorItem.getMaterial() == material
        ), ArmorItem.class).map(ArmorItem::getSlot).forEach(slots::add);
        return !slots.isEmpty() && new HashSet<>(slots).containsAll(Arrays.stream(MiscHelper.ARMOR_EQUIPMENT).toList());
    }

    public static boolean hasSetInInventory(Player player, ITieredItem.ItemTier armorTier) {
        List<EquipmentSlot> slots = new ArrayList<>();
        MiscHelper.instanceStream(allInventory(player.getInventory()).stream().filter(
                stack -> stack.getItem() instanceof TieredArmorItem && ITieredItem.getTier(stack) == armorTier
        ).map(ItemStack::getItem), TieredArmorItem.class).map(ArmorItem::getSlot).forEach(slots::add);
        return !slots.isEmpty() && new HashSet<>(slots).containsAll(Arrays.stream(MiscHelper.ARMOR_EQUIPMENT).toList());
    }

    public static boolean hasInInventory(List<ItemStack> content, Player player) {
        return getRemaining(content, player).isEmpty();
    }

    public static List<ItemStack> getRemaining(List<ItemStack> content, Player player) {
        List<ItemStack> ret = new ArrayList<>();
        for (ItemStack stack : content) {
            List<ItemStack> list = getByFilter(player, stack1 -> {
                MiscHelper.ensureTags(stack, stack1);
                return ItemStack.isSameItemSameTags(stack, stack1);
            });
            list.forEach(stack1 -> stack.shrink(stack1.getCount()));
            if (stack.getCount() > 0) {
                ret.add(stack);
            }
        }
        return ret;
    }
}