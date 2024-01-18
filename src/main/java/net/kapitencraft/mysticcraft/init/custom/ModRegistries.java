package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.init.ModGlyphEffects;
import net.kapitencraft.mysticcraft.init.ModRequirements;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface ModRegistries {
    Registry<GlyphEffect> GLYPH_REGISTRY = BuiltInRegistries.forge(ModRegistryKeys.GLYPH_EFFECTS, registry -> ModGlyphEffects.RAINBOW.get());
    Registry<Requirement> REQUIREMENT_REGISTRY = BuiltInRegistries.forge(ModRegistryKeys.REQUIREMENTS, registry -> ModRequirements.NONE);
}