package net.kapitencraft.mysticcraft.entity.armor.client;

import net.kapitencraft.mysticcraft.item.WizardHatItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WizardHatRenderer extends GeoArmorRenderer<WizardHatItem> {
    public WizardHatRenderer() {
        super(new WizardHatModel());

    }
}
