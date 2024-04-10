package net.kapitencraft.mysticcraft.item.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class SoulbindHelper {
    public static final String SOULBOUND_TAG_ID = "Soulbound";

    public static boolean isSoulbound(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(SOULBOUND_TAG_ID, Tag.TAG_BYTE) && tag.getBoolean(SOULBOUND_TAG_ID);
    }

    public static boolean isNotSoulbound(ItemStack stack) {
        return !isSoulbound(stack);
    }

    public static void setSoulbound(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(SOULBOUND_TAG_ID, true);
    }
}
