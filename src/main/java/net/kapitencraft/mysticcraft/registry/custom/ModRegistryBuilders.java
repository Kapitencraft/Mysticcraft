package net.kapitencraft.mysticcraft.registry.custom;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRegistryBuilders {
    RegistryBuilder<Spell> SPELLS = makeBuilder(ModRegistryKeys.SPELLS);
    RegistryBuilder<Codec<? extends PerkReward>> PERK_REWARDS = makeBuilder(ModRegistryKeys.PERK_REWARDS);

    private static <T> RegistryBuilder<T> makeBuilder(ResourceKey<Registry<T>> location) {
        return new RegistryBuilder<T>().setName(location.location());
    }
}