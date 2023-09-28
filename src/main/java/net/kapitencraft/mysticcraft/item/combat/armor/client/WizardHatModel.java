package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.WizardHatItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class WizardHatModel extends DefaultedItemGeoModel<WizardHatItem> {
    public WizardHatModel() {
        super(new ResourceLocation(MysticcraftMod.MOD_ID, "wizard_hat_model"));
    }
}