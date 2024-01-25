package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public interface ModRegistries {
    //TODO fix shit crashing
    IForgeRegistry<GlyphEffect> GLYPH_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.GLYPH_EFFECTS);
    IForgeRegistry<Requirement<?>> REQUIREMENT_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.REQUIREMENTS);
}