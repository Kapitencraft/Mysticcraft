package net.kapitencraft.mysticcraft.client.font.effect;

/**
 * base class for the glyph effect
 */
public abstract class GlyphEffect {

    public GlyphEffect() {
    }

    public abstract void apply(EffectSettings settings);

    public abstract char getKey();
}