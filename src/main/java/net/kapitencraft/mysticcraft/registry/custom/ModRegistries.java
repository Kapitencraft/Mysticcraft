package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

/**
 * class that contains the {@link IForgeRegistry}s for the custom registries
 */
public interface ModRegistries {
    IForgeRegistry<ReforgingBonus> REFORGE_BONUSES_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.REFORGE_BONUSES);
}