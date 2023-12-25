package net.kapitencraft.mysticcraft.client.font.effect;

public abstract class BaseGlyphEffect {

    public BaseGlyphEffect() {
        if (Effects.EFFECTS_FOR_KEY.containsKey(getKey()))
            throw new IllegalStateException("tried registering Effect with duplicate id");
        Effects.EFFECTS_FOR_KEY.put(getKey(), this);
    }

    public abstract void apply(EffectSettings settings);

    abstract char getKey();
}