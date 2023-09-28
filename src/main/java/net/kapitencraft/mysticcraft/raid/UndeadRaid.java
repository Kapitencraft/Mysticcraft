package net.kapitencraft.mysticcraft.raid;

import com.google.common.collect.HashMultimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;

public class UndeadRaid extends ModRaid {
    protected UndeadRaid(BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay) {
        super(BossEvent.BossBarColor.RED, overlay);
    }

    @Override
    protected HashMultimap<Integer, LivingEntity> getMobWeights() {
        return null;
    }

    @Override
    protected Component getRaidName() {
        return Component.translatable("event.mysticcraft.undead_raid");
    }
}
