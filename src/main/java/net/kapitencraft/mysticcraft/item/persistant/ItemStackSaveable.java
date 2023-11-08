package net.kapitencraft.mysticcraft.item.persistant;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public abstract class ItemStackSaveable {
    private static final HashMap<ItemStack, HashMap<SaveableVariant<?>, ItemStackSaveable>> MAP = new HashMap<>();
    public static <T extends ItemStackSaveable> T get(ItemStack stack, SaveableVariant<T> variant) {
        if (MAP.containsKey(stack)) {
            return MAP.get(stack);
        } else {
            MAP.put(stack, )
        }
    }


}
