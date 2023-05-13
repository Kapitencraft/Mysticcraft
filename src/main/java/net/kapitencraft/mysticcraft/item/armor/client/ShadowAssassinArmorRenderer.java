package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.armor.ShadowAssassinArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAssassinArmorRenderer extends GeoArmorRenderer<ShadowAssassinArmorItem> {
    public ShadowAssassinArmorRenderer() {
        super(new ShadowAssassinArmorModel());
        grabRelevantBones(getGeoModel().getBakedModel(getGeoModel().getModelResource(this.animatable)));
    }

    public void hide(boolean shouldBeInvisible) {
        MysticcraftMod.sendInfo(String.valueOf(shouldBeInvisible));
        this.setAllVisible(!shouldBeInvisible);
    }
}
