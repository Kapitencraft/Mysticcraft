package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * class used for the Resource Keys from custom registries
 */
public class ModRegistryKeys {

    public static final ResourceKey<Registry<Spell>> SPELLS = createRegistry("spells");

    private static <T> ResourceKey<Registry<T>> createRegistry(String id) {
        return ResourceKey.createRegistryKey(MysticcraftMod.res(id));
    }
}