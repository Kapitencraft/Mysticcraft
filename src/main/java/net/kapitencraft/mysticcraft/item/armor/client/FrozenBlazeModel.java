package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.armor.FrozenBlazeArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class FrozenBlazeModel extends DefaultedItemGeoModel<FrozenBlazeArmorItem> {
    public FrozenBlazeModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "frozen_blaze_armor"));
    }
}
