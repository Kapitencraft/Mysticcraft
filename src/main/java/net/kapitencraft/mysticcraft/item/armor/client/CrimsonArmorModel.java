package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.armor.CrimsonArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class CrimsonArmorModel extends DefaultedItemGeoModel<CrimsonArmorItem> {
    public CrimsonArmorModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "crimson_armor"));
    }
}
