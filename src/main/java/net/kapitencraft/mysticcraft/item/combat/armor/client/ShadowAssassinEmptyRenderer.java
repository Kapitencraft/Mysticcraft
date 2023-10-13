package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.ShadowAssassinArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAssassinEmptyRenderer extends GeoArmorRenderer<ShadowAssassinArmorItem> {
    public ShadowAssassinEmptyRenderer() {
        super(new ShadowAssassinArmorModel().withAltTexture(MysticcraftMod.res("shadow_assassin_empty")));
    }
}
