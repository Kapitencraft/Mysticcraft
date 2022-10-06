package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWildItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StaffOfTheWildModel extends AnimatedGeoModel<StaffOfTheWildItem> {
    @Override
    public ResourceLocation getModelResource(StaffOfTheWildItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "geo/staff_of_the_wild.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StaffOfTheWildItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "textures/item/staff_of_the_wild.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StaffOfTheWildItem animateAble) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "animations/staff_of_the_wild.animation.json");
    }
}
