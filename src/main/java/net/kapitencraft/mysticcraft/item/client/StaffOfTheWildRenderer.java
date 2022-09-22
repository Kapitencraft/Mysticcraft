package net.kapitencraft.mysticcraft.item.client;

import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWildItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class StaffOfTheWildRenderer extends GeoItemRenderer<StaffOfTheWildItem> {
    public StaffOfTheWildRenderer() {
        super(new StaffOfTheWildModel());
    }
}
