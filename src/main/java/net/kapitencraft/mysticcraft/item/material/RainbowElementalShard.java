package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.spell.Element;

public class RainbowElementalShard extends ElementalShard {
    private static final Element RAINBOW = () -> "rainbow";
    public RainbowElementalShard() {
        super(RAINBOW);
    }
}
