package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRegistryBuilders {
    RegistryBuilder<Spell> REFORGE_BONUSES_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.SPELLS);

    private static <T> RegistryBuilder<T> makeBuilder(ResourceKey<Registry<T>> location) {
        return new RegistryBuilder<T>().setName(location.location());
    }
}