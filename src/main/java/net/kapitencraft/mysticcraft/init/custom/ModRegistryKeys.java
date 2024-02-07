package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * class used for the Resource Keys from custom registries (like {@link GlyphEffect})
 */
public class ModRegistryKeys {

    public static final ResourceKey<Registry<GlyphEffect>> GLYPH_EFFECTS = createRegistry("glyph_effects");
    public static final ResourceKey<Registry<Requirement<?>>> REQUIREMENTS = createRegistry("requirements");

    private static <T> ResourceKey<Registry<T>> createRegistry(String id) {
        return ResourceKey.createRegistryKey(MysticcraftMod.res(id));
    }
}