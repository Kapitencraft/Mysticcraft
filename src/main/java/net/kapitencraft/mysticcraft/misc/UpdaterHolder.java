package net.kapitencraft.mysticcraft.misc;

import java.util.HashMap;

public class UpdaterHolder<K, V> {
    HashMap<K, V> updates = new HashMap<>();

    public void addUpdate(K key, V value) {
        updates.put(key, value);
    }

    public void updateAll(HashMap<K, V> toUpdate) {
        for (K key : updates.keySet()) {
            toUpdate.remove(key);
            toUpdate.put(key, updates.get(key));
        }
        updates.clear();
    }
}
