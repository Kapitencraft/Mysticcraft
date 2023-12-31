package net.kapitencraft.mysticcraft.client.font.effect;

import net.minecraft.Util;
import net.minecraft.util.Mth;

public class RainbowEffect extends BaseGlyphEffect {
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
    char getKey() {
        return 'o';
    }
}
