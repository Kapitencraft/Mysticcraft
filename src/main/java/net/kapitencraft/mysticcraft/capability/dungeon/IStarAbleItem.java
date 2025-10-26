package net.kapitencraft.mysticcraft.capability.dungeon;

import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IStarAbleItem extends IReAnUpgradeable {
    char STAR = '☆';
    int MAX_STARS = 25;
    String TAG_ID = "StarData";
    List<String> COLOR_FOR_STAR_ROW = List.of("§6", "§5", "§b", "§c", "§4");
    static MutableComponent getStarDisplay(ItemStack stack) {
        int stars = getStars(stack);
        int tillNextColor = stars % 5;
        int row = (stars - tillNextColor) / 5;
        StringBuilder builder = new StringBuilder();
        if (tillNextColor > 0) builder.append(COLOR_FOR_STAR_ROW.get(row));
        for (int i = 0; i < Math.min(stars, 5); i++) {
            if (i == tillNextColor) {
                builder.append(COLOR_FOR_STAR_ROW.get(row-1));
            }
            builder.append(STAR);
        }
        return Component.literal(builder.toString());
    }

    static boolean hasStars(ItemStack stack) { return getStars(stack) > 0; }

    static int getStars(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.STARS, 0);
    }

    static void setStars(ItemStack stack, int stars) {
        stack.set(ModDataComponentTypes.STARS, stars);
    }

    default ItemStack upgrade(ItemStack in) {
        setStars(in, Math.min(MAX_STARS, getStars(in) + 1));
        return in;
    }

    @Override
    default boolean mayUpgrade(ItemStack stack) {
        return getStars(stack) < getMaxStars(stack);
    }

    int getMaxStars(ItemStack stack);
}