package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.client.render.ColorAnimator;
import net.kapitencraft.mysticcraft.spell.Element;

public class RainbowElementalShard extends ElementalShard {
    private static final ColorAnimator animator = ColorAnimator.createLightRainbow(100);
    private static final Element RAINBOW = () -> "rainbow";
    public RainbowElementalShard() {
        super(RAINBOW);
    }
}
