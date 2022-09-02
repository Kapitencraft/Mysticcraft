package net.kapitencraft.mysticcraft.entity.client.armor;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.WizardHatItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WizardHatModel extends AnimatedGeoModel<WizardHatItem> {
    @Override
    public ResourceLocation getModelResource(WizardHatItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "geo/wizard_hat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WizardHatItem object) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "textures/models/armor/wizard_hat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WizardHatItem animatable) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "animations/armor_animations.json");
    }
}
