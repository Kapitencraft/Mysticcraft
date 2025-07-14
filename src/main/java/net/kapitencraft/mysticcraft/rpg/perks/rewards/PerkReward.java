package net.kapitencraft.mysticcraft.rpg.perks.rewards;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public interface PerkReward {
    Codec<PerkReward> CODEC = ModRegistries.PERK_REWARDS.getCodec().dispatchStable(PerkReward::getCodec, Function.identity());

    void grant(int level, Player player);

    void revoke(Player player);

    Codec<? extends PerkReward> getCodec();

    interface Builder {

        PerkReward build();
    }
}
