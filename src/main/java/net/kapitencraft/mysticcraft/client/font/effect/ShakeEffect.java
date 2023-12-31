package net.kapitencraft.mysticcraft.client.font.effect;

import net.minecraft.Util;
import net.minecraft.util.Mth;

public class ShakeEffect extends BaseGlyphEffect {
    @Override
    public void apply(EffectSettings settings) {
        settings.x += Mth.sin(Util.getMillis() * 0.01F + settings.index) * 2;
    }

    @Override
    char getKey() {
        return 0;
    }
}
