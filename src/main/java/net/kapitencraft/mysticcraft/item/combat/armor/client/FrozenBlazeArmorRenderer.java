package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.item.combat.armor.FrozenBlazeArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FrozenBlazeArmorRenderer extends GeoArmorRenderer<FrozenBlazeArmorItem> {
    public FrozenBlazeArmorRenderer() {
        super(new FrozenBlazeArmorModel());
    }
}
