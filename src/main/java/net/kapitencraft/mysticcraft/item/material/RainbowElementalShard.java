package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.mysticcraft.spell.Element;

public class RainbowElementalShard extends ElementalShard {
    private static final Element RAINBOW = () -> "rainbow";

    public RainbowElementalShard(Properties properties) {
        super(RAINBOW, properties.rarity(ExtraRarities.LEGENDARY));
    }
}
