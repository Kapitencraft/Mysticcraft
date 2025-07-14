package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.StatPerkReward;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface PerkRewards {
    DeferredRegister<Codec<? extends PerkReward>> REGISTRY = MysticcraftMod.registry(ModRegistryKeys.PERK_AWARDS);

    RegistryObject<Codec<StatPerkReward>> STAT = REGISTRY.register("stat", () -> StatPerkReward.CODEC);
}
