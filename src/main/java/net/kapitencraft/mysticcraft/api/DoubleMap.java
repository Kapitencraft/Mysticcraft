package net.kapitencraft.mysticcraft.api;

import net.kapitencraft.mysticcraft.helpers.CollectionHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DoubleMap<T, K, L> extends HashMap<T, HashMap<K, L>> {
    private boolean immutable = false;

    public void forValues(Consumer<L> consumer) {
        CollectionHelper.merge(this.values().stream().map(Map::values).toList()).forEach(consumer);
    }

    @Override
    public HashMap<K, L> put(T key, HashMap<K, L> value) {
        if (this.immutable) throw new UnsupportedOperationException("tried modifying immutable double map");
        return super.put(key, value);
    }

    public DoubleMap<T, K, L> immutable() {
        this.immutable = true;
        return this;
    }


    public void forMap(BiConsumer<K, L> biConsumer) {
        this.values().forEach(map -> map.forEach(biConsumer));
    }

    public static <T, K, L> DoubleMap<T, K, L> create() {
        return new DoubleMap<>();
    }

    public void put(T t, K k, L l) {
        if (this.immutable) throw new UnsupportedOperationException("tried modifying immutable double map");
        if (this.containsKey(t)) {
            this.get(t).put(k, l);
        } else {
            HashMap<K, L> map = new HashMap<>();
            map.put(k, l);
            this.put(t, map);
        }
    }

    public L getOrAdd(T t, K k, L ifAbsent) {
        if (this.immutable) throw new UnsupportedOperationException("tried modifying immutable double map");
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
