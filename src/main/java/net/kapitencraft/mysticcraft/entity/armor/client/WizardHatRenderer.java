package net.kapitencraft.mysticcraft.entity.armor.client;

import net.kapitencraft.mysticcraft.item.WizardHatItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class WizardHatRenderer extends GeoArmorRenderer<WizardHatItem> {
    public WizardHatRenderer() {
        super(new WizardHatModel());

        this.headBone = "bone";
        this.bodyBone = null;
        this.leftArmBone = null;
        this.rightArmBone = null;
        this.rightBootBone = null;
        this.rightLegBone = null;
        this.leftBootBone = null;
        this.leftLegBone = null;
    }
}
