package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.item.combat.armor.CrimsonArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrimsonArmorRenderer extends GeoArmorRenderer<CrimsonArmorItem> {
    public CrimsonArmorRenderer() {
        super(new CrimsonArmorModel());
    }
}
