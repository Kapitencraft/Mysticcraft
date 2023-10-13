package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class LavaFishItem extends Item implements IModItem {
    public static final TabGroup LAVA_FISH_GROUP = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public LavaFishItem(int nutrition, float saturationModifier, MobEffectInstance instance) {
        super(new Properties().fireResistant().food(new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationModifier).effect(()-> instance, 1).build()));
    }

    @Override
    public TabGroup getGroup() {
        return LAVA_FISH_GROUP;
    }
}
