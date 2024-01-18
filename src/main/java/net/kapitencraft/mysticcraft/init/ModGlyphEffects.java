package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.RainbowEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.ShakeEffect;
import net.kapitencraft.mysticcraft.client.font.effect.effects.WaveEffect;
import net.kapitencraft.mysticcraft.init.custom.ModRegistryKeys;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.stream.Collectors;

public interface ModGlyphEffects {
    DeferredRegister<GlyphEffect> REGISTRY = MysticcraftMod.makeCustomRegistry(ModRegistryKeys.GLYPH_EFFECTS, RegistryBuilder::new);
    static Map<Character, GlyphEffect> effectsForKey() {
        return REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toMap(GlyphEffect::getKey, effect -> effect));
    }

    RegistryObject<RainbowEffect> RAINBOW = REGISTRY.register("rainbow", RainbowEffect::new);
    RegistryObject<WaveEffect> WAVE = REGISTRY.register("wave", WaveEffect::new);
    RegistryObject<ShakeEffect> SHAKE = REGISTRY.register("shake", ShakeEffect::new);

}