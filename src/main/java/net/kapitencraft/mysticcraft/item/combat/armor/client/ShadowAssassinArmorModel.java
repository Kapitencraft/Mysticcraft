package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.ShadowAssassinArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ShadowAssassinArmorModel extends DefaultedItemGeoModel<ShadowAssassinArmorItem> {
    public ShadowAssassinArmorModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "shadow_assassin_armor"));
    }
}
