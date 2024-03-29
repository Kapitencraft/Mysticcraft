package net.kapitencraft.mysticcraft.client.font.effect.effects;

import net.kapitencraft.mysticcraft.client.font.effect.EffectSettings;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.minecraft.Util;
import net.minecraft.util.Mth;

public class RainbowEffect extends GlyphEffect {
    @Override
    public void apply(EffectSettings settings) {
        if (settings.isShadow) {
            return;
        }
        int color = Mth.hsvToRgb(((Util.getMillis() * 0.02f + settings.index) % 30) / 30, 0.8F, 0.8F);
        settings.r = (color >> 16 & 255) / 255F;
        settings.g = (color >> 8 & 255) / 255F;
        settings.b = (color & 255) / 255F;
    }

    @Override
    public char getKey() {
        return 'z';
    }
}
