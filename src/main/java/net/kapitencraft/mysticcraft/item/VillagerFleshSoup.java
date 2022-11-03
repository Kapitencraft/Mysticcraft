package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VillagerFleshSoup extends Item {
    private static final FoodProperties properties = new FoodProperties.Builder().meat().saturationMod(0).build();

    public VillagerFleshSoup(Properties p_41383_) {
        super(new Properties().tab(CreativeModeTab.TAB_FOOD).rarity(Rarity.RARE).food(properties));
    }
}
