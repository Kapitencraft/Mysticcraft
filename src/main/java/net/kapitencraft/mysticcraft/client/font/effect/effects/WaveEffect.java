package net.kapitencraft.mysticcraft.client.font.effect.effects;

import net.kapitencraft.mysticcraft.client.font.effect.EffectSettings;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public class WaveEffect extends GlyphEffect {
    @Override
    public void apply(EffectSettings settings) {
        settings.y += Mth.sin(Util.getMillis() * 0.01F + settings.index) * 2;
    }

    @Override
    public char getKey() {
        return 'w';
    }
}
