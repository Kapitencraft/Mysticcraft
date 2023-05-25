package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.armor.ShadowAssassinArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAssassinEmptyRenderer extends GeoArmorRenderer<ShadowAssassinArmorItem> {
    public ShadowAssassinEmptyRenderer() {
        super(new ShadowAssassinArmorModel().withAltTexture(new ResourceLocation(MysticcraftMod.MOD_ID, "shadow_assassin_empty")));
    }
}
