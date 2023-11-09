package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DoubleMap<T, K, L> extends HashMap<T, HashMap<K, L>> {

    public void forValues(Consumer<L> consumer) {
        this.values().stream().map(Map::values).forEach(col -> col.forEach(consumer));
    }
    public void forMap(BiConsumer<K, L> biConsumer) {
        this.values().forEach(map -> map.forEach(biConsumer));
    }

    public static <T, K, L> DoubleMap<T, K, L> create() {
        return new DoubleMap<>();
    }

    public void put(T t, K k, L l) {
        if (this.containsKey(t)) {
            this.get(t).put(k, l);
        } else {
            HashMap<K, L> map = new HashMap<>();
            map.put(k, l);
            this.put(t, map);
        }
    }

    public L getOrAdd(T t, K k, L ifAbsent) {
        if (this.containsKey(t)) {
            this.get(t).putIfAbsent(k, ifAbsent);
        } else {
            this.put(t, new HashMap<>());
            this.get(t).put(k, ifAbsent);
        }
        return get(t, k);
    }

    public L get(T t, K k) {
        return this.get(t).get(k);
    }

    public L getOrNull(T t, K k) {
        try {
            return get(t, k);
        } catch (Exception e) {
            return null;
        }
    }
}
