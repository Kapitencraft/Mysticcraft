package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.spells.StaffOfTheWildItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class StaffOfTheWildModel extends DefaultedItemGeoModel<StaffOfTheWildItem> {
    public StaffOfTheWildModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "staff_of_the_wild"));
    }
}
