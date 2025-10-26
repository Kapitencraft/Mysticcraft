package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;

public class SoulbindHelper {

    public static boolean isSoulbound(ItemStack stack) {
        return stack.has(ModDataComponentTypes.SOUL_BOUND);
    }

    public static boolean isNotSoulbound(ItemStack stack) {
        return !isSoulbound(stack);
    }

    public static void setSoulbound(ItemStack stack) {
        stack.set(ModDataComponentTypes.SOUL_BOUND, Unit.INSTANCE);
    }
}
