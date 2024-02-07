package net.kapitencraft.mysticcraft.item.capability.dungeon;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IStarAbleItem extends IReAnUpgradeable {
    char STAR = '\u2606';
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
        return stack.getOrCreateTag().getInt(TAG_ID);
    }

    static void setStars(ItemStack stack, int stars) {
        stack.getOrCreateTag().putInt(TAG_ID, stars);
    }

    default ItemStack upgrade(ItemStack in) {
        setStars(in, getStars(in) + 1);
        return in;
    }

    @Override
    default boolean mayUpgrade(ItemStack stack) {
        return getStars(stack) < getMaxStars(stack);
    }

    static Multimap<Attribute, AttributeModifier> modifyData(ItemStack stack, Multimap<Attribute, AttributeModifier> map) {
        return AttributeHelper.increaseByPercent(map, getStars(stack) * 0.02, AttributeModifier.Operation.values(), null);
    }

    int getMaxStars(ItemStack stack);
}