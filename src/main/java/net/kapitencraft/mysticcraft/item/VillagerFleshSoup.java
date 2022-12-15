package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VillagerFleshSoup extends Item {
    private static final FoodProperties properties = new FoodProperties.Builder().meat().saturationMod(0).build();

    public VillagerFleshSoup() {
        super(new Properties().rarity(Rarity.RARE).food(properties));
    }
}
