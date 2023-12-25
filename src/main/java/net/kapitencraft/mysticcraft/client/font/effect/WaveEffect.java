package net.kapitencraft.mysticcraft.client.font.effect;

import net.minecraft.Util;
import net.minecraft.util.Mth;

public class WaveEffect extends BaseGlyphEffect {
    @Override
    public void apply(EffectSettings settings) {
        settings.y += Mth.sin(Util.getMillis() * 0.01F + settings.index) * 2;
    }

    @Override
    char getKey() {
        return 'w';
    }
}
