package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRegistryBuilders {
    RegistryBuilder<GlyphEffect> GLYPH_EFFECT_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.GLYPH_EFFECTS.location());
    RegistryBuilder<Requirement<?>> REQUIREMENT_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REQUIREMENTS.location());
    RegistryBuilder<IRequestable<?, ?>> REQUESTABLE_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REQUESTABLES.location());

    private static <T> RegistryBuilder<T> makeBuilder(ResourceLocation location) {
        return new RegistryBuilder<T>().setName(location);
    }
}