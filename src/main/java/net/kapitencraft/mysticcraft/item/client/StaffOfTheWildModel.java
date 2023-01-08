package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWild;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class StaffOfTheWildModel extends DefaultedItemGeoModel<StaffOfTheWild> {
    public StaffOfTheWildModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "staff_of_the_wild"));
    }
}
