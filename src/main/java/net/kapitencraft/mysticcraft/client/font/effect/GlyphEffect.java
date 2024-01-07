package net.kapitencraft.mysticcraft.client.font.effect;

public abstract class GlyphEffect {

    public GlyphEffect() {
    }

    public abstract void apply(EffectSettings settings);

    public abstract char getKey();
}