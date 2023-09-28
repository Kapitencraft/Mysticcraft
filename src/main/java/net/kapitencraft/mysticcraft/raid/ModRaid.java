package net.kapitencraft.mysticcraft.raid;

import com.google.common.collect.HashMultimap;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ModRaid {

    private final ServerBossEvent event;

    protected ModRaid(BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay) {
        event = new ServerBossEvent(getRaidName(), color, overlay);
        event.setProgress(0);
    }

    protected abstract HashMultimap<Integer, LivingEntity> getMobWeights();

    protected abstract Component getRaidName();

    private int getHighestWeight() {
        int weight = 0;
        HashMultimap<Integer, LivingEntity> map = getMobWeights();
        for (int i : map.keySet()) {
            if (i <= 0) throw new IllegalArgumentException("Weight Map may not contain any mob with 0 weight");
            if (i > weight) weight = i;
        }
        if (weight > 0) {
            return weight;
        }
        throw new IllegalStateException("Weight Map must contain at least one content!");
    }

    private List<Integer> getAllWeights() {
        List<Integer> list = new ArrayList<>();
        for (int i : getMobWeights().keys()) {
            if (!list.contains(i)) {
                list.add(i);
            }
        }
        return list.stream().sorted(Comparator.comparingInt(Integer::intValue)).toList();
    }

    protected List<LivingEntity> makeWave(int weight) {
        List<LivingEntity> toSpawn = new ArrayList<>();
        HashMultimap<Integer, LivingEntity> weights = getMobWeights();
        List<Integer> allWeights = getAllWeights();
        while (weight > 0) {
            for (int i = allWeights.size() - 1; i > 0; i--) {
                if (i <= weight) {
                    List<LivingEntity> mobs = weights.get(i).stream().toList();
                    if (!mobs.isEmpty()) {
                        if (MathUtils.chance(0.6, null)) {
                            toSpawn.add(MathUtils.pickRandom(mobs));
                            weight -= i;
                            break;
                        }
                    }
                }
            }
        }
        return toSpawn;
    }
}