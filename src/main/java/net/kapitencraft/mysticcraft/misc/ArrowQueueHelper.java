package net.kapitencraft.mysticcraft.misc;

import java.util.ArrayList;
import java.util.UUID;

public class ArrowQueueHelper {
    private final ArrayList<UUID> arrows = new ArrayList<>();
    boolean saveToQueue = false;
    private final ArrayList<UUID> queue = new ArrayList<>();

    public interface Run {
        void run(UUID uuid);
    }

    public void add(UUID uuid) {
        if (saveToQueue) {
            if (!queue.contains(uuid)) queue.add(uuid);
        } else {
            if (!arrows.contains(uuid)) arrows.add(uuid);
        }
    }

    public boolean remove(UUID uuid) {
        return !saveToQueue && arrows.remove(uuid);
    }

    public void update(Run run) {
        saveToQueue = true;
        arrows.forEach(run::run);
        saveToQueue = false;
        arrows.addAll(queue);
        queue.clear();
    }
}
