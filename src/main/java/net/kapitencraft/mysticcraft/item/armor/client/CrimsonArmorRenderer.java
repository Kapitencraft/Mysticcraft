package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.item.armor.CrimsonArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrimsonArmorRenderer extends GeoArmorRenderer<CrimsonArmorItem> {
    public CrimsonArmorRenderer() {
        super(new CrimsonArmorModel());
    }
}
