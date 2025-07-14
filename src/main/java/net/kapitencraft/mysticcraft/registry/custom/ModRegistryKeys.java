package net.kapitencraft.mysticcraft.registry.custom;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * class used for the Resource Keys from custom registries
 */
public interface ModRegistryKeys {

    ResourceKey<Registry<Spell>> SPELLS = createRegistry("spells");
    ResourceKey<Registry<Codec<? extends PerkReward>>> PERK_AWARDS = createRegistry("perk_awards");

    private static <T> ResourceKey<Registry<T>> createRegistry(String id) {
        return ResourceKey.createRegistryKey(MysticcraftMod.res(id));
    }
}