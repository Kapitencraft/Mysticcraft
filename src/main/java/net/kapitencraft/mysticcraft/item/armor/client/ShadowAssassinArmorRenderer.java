package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.item.armor.ShadowAssassinArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ShadowAssassinArmorRenderer extends GeoArmorRenderer<ShadowAssassinArmorItem> {
    public ShadowAssassinArmorRenderer() {
        super(new ShadowAssassinArmorModel());
    }

    public void hide(boolean shouldBeInvisible) {
        this.head.setHidden(shouldBeInvisible);
        this.body.setHidden(shouldBeInvisible);
        this.leftArm.setHidden(shouldBeInvisible);
        this.rightArm.setHidden(shouldBeInvisible);
        this.leftLeg.setHidden(shouldBeInvisible);
        this.rightLeg.setHidden(shouldBeInvisible);
        this.leftBoot.setHidden(shouldBeInvisible);
        this.rightBoot.setHidden(shouldBeInvisible);
    }
}
