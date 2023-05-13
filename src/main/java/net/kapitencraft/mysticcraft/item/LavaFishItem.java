package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class LavaFishItem extends Item {
    public LavaFishItem(int nutrition, float saturationModifier, MobEffectInstance instance) {
        super(new Properties().fireResistant().food(new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationModifier).effect(()-> instance, 1).build()));
    }
}
