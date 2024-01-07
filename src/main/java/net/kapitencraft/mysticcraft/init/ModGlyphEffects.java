package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.RainbowEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.ShakeEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.WaveEffect;
import net.kapitencraft.mysticcraft.init.custom.ModRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.stream.Collectors;

public class ModGlyphEffects {
    public static final DeferredRegister<GlyphEffect> REGISTRY = MysticcraftMod.makeRegistry(ModRegistries.GLYPH_EFFECTS, RegistryBuilder::new);
    public static Map<Character, GlyphEffect> effectsForKey() {
        return REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toMap(GlyphEffect::getKey, effect -> effect));
    }

    public static final RegistryObject<RainbowEffect> RAINBOW = REGISTRY.register("rainbow", RainbowEffect::new);
    public static final RegistryObject<WaveEffect> WAVE = REGISTRY.register("wave", WaveEffect::new);
    public static final RegistryObject<ShakeEffect> SHAKE = REGISTRY.register("shake", ShakeEffect::new);

}