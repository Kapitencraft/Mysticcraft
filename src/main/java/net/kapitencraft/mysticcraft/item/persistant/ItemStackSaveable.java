package net.kapitencraft.mysticcraft.item.persistant;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public abstract class ItemStackSaveable {

    public static void save(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        SaveableVariant.SERIALIZERS.forEach(variant -> {
            if (variant.getContainer().contains(stack)) {
                tag.put(variant.getName(), variant.save(stack));
            }
        });
    }

    public static <T extends ItemStackSaveable> T get(ItemStack stack, SaveableVariant<T> variant) {
        return variant.getContainer().get(stack);
    }

    public static void load(ItemStack stack) {
        SaveableVariant.SERIALIZERS.forEach(variant -> {
            try {
                variant.loadAndSave(stack);
            } catch (Exception ignored) {
            }
        });
    }
}