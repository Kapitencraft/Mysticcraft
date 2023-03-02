package net.kapitencraft.mysticcraft.item.armor.client;

import net.kapitencraft.mysticcraft.item.armor.FrozenBlazeArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FrozenBlazeRenderer extends GeoArmorRenderer<FrozenBlazeArmorItem> {
    public FrozenBlazeRenderer() {
        super(new FrozenBlazeModel());
    }
}
