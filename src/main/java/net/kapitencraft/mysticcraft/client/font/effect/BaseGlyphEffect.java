package net.kapitencraft.mysticcraft.client.font.effect;

public abstract class BaseGlyphEffect {

    public BaseGlyphEffect() {
    }

    public abstract void apply(EffectSettings settings);

    abstract char getKey();
}