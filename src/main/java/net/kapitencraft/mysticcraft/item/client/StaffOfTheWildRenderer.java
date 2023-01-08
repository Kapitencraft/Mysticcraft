package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWild;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class StaffOfTheWildRenderer extends GeoItemRenderer<StaffOfTheWild> {
    public StaffOfTheWildRenderer() {
        super(new StaffOfTheWildModel());
    }
}
