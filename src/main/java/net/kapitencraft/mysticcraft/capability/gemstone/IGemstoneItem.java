package net.kapitencraft.mysticcraft.capability.gemstone;

import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IGemstoneItem {
    String id = "GemstoneData";

    static <T extends Item & IGemstoneItem> ItemStack createData(GemstoneType.Rarity rarity, GemstoneType type, Supplier<T> supplier) {
        ItemStack stack = new ItemStack(supplier.get());
        stack.set(ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(type, rarity));
        return stack;
    }

    static int getColor(@NotNull ItemStack stack) {
        return getGemstone(stack).getColor() | 0xFF000000;
    }

    static GemstoneType.Rarity getGemRarity(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.ROUGH)).rarity();
    }

    static String getGemId(ItemStack stack) {
        return getGemstone(stack).getId();
    }

    static GemstoneType getGemstone(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.ROUGH)).type();
    }

}
