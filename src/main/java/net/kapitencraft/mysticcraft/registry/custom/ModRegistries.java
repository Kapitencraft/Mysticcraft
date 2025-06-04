package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

/**
 * class that contains the {@link IForgeRegistry}s for the custom registries
 */
@SuppressWarnings("UnstableApiUsage")
public interface ModRegistries {
    IForgeRegistry<Spell> SPELLS = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.SPELLS);
}