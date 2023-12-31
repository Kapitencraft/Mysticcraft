package net.kapitencraft.mysticcraft.item.data.gemstone;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IGemstoneItem {
    String id = "GemstoneData";

    static ItemStack createData(GemstoneType.Rarity rarity, GemstoneType type, boolean block) {
        ItemStack stack = new ItemStack(block ? ModBlocks.GEMSTONE_BLOCK.getItem() : ModItems.GEMSTONE.get());
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
