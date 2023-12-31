package net.kapitencraft.mysticcraft.client.font.effect;

import java.util.HashMap;
import java.util.function.Supplier;

public class GlyphEffects {
    public static final HashMap<Character, BaseGlyphEffect> EFFECTS_FOR_KEY = new HashMap<>();
    public static RainbowEffect RAINBOW = register(RainbowEffect::new);
    public static WaveEffect WAVE = register(WaveEffect::new);
    public static ShakeEffect SHAKE = register(ShakeEffect::new);


    private static  <T extends BaseGlyphEffect> T register(Supplier<T> effectSup) {
        T effect = effectSup.get();
        EFFECTS_FOR_KEY.put(effect.getKey(), effect);
        return effect;
    }
}