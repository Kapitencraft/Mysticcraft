package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class ParticleHelperQueueWorker {
    private final Multimap<UUID, ParticleHelper> multimap = HashMultimap.create();
    private final Multimap<UUID, ParticleHelper> queue = HashMultimap.create();
    private boolean joinQueue = false;


    public boolean hasTarget(UUID targetUUID) {
        return multimap.containsKey(targetUUID);
    }
    public ParticleHelperQueueWorker() {}

    public void tick(int ticks, UUID toTick) {
        joinQueue = true;
        for (ParticleHelper helper : multimap.get(toTick)) {
            helper.tick(ticks);
        }
        joinQueue = false;
        this.multimap.putAll(queue);
        queue.clear();
    }

    public void add(UUID uuid, ParticleHelper helper) {
        if (joinQueue) {
            queue.put(uuid, helper);
        } else {
            multimap.put(uuid, helper);
        }
    }

    private Collection<ParticleHelper> getHelper(String type, Collection<ParticleHelper> source) {
        Collection<ParticleHelper> helpers = new ArrayList<>();
        for (ParticleHelper helper : source) {
            if (Objects.equals(helper.getHelperReason(), type)) {
                helpers.add(helper);
            }
        }
        return helpers;
    }

    public boolean remove(UUID target, @Nullable String type) {
        if (!joinQueue) {
            for (ParticleHelper helper : getHelper(type, multimap.get(target))) {
                helper.remove(true);
                multimap.remove(target, helper);
            }
            return true;
        }
        return false;
    }
}
