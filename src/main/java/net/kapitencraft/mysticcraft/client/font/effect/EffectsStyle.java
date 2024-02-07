package net.kapitencraft.mysticcraft.client.font.effect;

import java.util.List;

/**
 * interface (or <i>duck-class</i>) for {@link GlyphEffect}s
 */
public interface EffectsStyle {

    void addEffect(GlyphEffect effect);
    List<GlyphEffect> getEffects();
}
