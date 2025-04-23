package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * class used for the Resource Keys from custom registries
 */
public class ModRegistryKeys {

    public static final ResourceKey<Registry<ReforgingBonus>> REFORGE_BONUSES = createRegistry("reforge_bonuses");

    private static <T> ResourceKey<Registry<T>> createRegistry(String id) {
        return ResourceKey.createRegistryKey(MysticcraftMod.res(id));
    }
}