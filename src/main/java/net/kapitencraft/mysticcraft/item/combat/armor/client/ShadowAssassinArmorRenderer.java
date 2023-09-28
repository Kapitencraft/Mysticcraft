package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.item.combat.armor.ShadowAssassinArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAssassinArmorRenderer extends GeoArmorRenderer<ShadowAssassinArmorItem> {
    public ShadowAssassinArmorRenderer() {
        super(new ShadowAssassinArmorModel());
        grabRelevantBones(getGeoModel().getBakedModel(getGeoModel().getModelResource(this.animatable)));
    }
}
