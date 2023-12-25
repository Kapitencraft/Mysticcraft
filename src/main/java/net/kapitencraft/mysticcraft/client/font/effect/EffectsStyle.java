package net.kapitencraft.mysticcraft.client.font.effect;

import java.util.List;

public interface EffectsStyle {

    void addEffect(BaseGlyphEffect effect);
    List<BaseGlyphEffect> getEffects();
}
