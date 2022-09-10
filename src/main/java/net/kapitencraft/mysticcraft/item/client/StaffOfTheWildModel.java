package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.StaffOfTheWildItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StaffOfTheWildModel extends AnimatedGeoModel<StaffOfTheWildItem> {

    @Override
    public ResourceLocation getModelResource(StaffOfTheWildItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "geo/StaffOfTheWild.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StaffOfTheWildItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "textures/items/staff_of_the_wild.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StaffOfTheWildItem animatable) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "animations/staff_of_the_wild.animation.json");
    }
}
