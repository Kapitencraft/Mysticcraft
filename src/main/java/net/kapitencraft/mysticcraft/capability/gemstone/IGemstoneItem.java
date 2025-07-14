package net.kapitencraft.mysticcraft.capability.gemstone;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IGemstoneItem {
    String id = "GemstoneData";

    static <T extends Item & IGemstoneItem> ItemStack createData(GemstoneType.Rarity rarity, GemstoneType type, Supplier<T> supplier) {
        ItemStack stack = new ItemStack(supplier.get());
        CompoundTag tag = stack.getOrCreateTagElement(id);
        tag.putString("GemId", type.getId());
        tag.putString("GemRarity", rarity.getSerializedName());
        return stack;
    }

    static int getColor(@NotNull ItemStack stack) {
        return getGemstone(stack).getColour();
    }

    static GemstoneType.Rarity getGemRarity(ItemStack stack) {
        return GemstoneType.Rarity.getById(stack.getOrCreateTagElement(id).getString("GemRarity"));
    }

    static String getGemId(ItemStack stack) {
        return stack.getOrCreateTagElement(id).getString("GemId");
    }

    static GemstoneType getGemstone(ItemStack stack) {
        return GemstoneType.getById(getGemId(stack));
    }

}
