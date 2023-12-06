package net.kapitencraft.mysticcraft.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

public class MapStream<T, K> {
    private final Map<T, K> map = new HashMap<>();

    public static <T, K> MapStream<T, K> of(Map<T, K> map) {
        MapStream<T, K> stream = new MapStream<>();
        stream.map.putAll(map);
        return stream;
    }

    public static <T, K> MapStream<T, K> create(List<T> keys, List<K> values) {
        if (keys.size() != values.size()) {
            throw new IllegalStateException("tried creating map from different length collections");
        }
        List<StreamEntry<T, K>> entries = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            entries.add(new StreamEntry<>(keys.get(i), values.get(i)));
        }
        return of(entries);
    }


    public <J> Stream<J> map(BiFunction<T, K, J> mapper) {
        List<J> list = new ArrayList<>();
        this.map.forEach((t, k) -> list.add(mapper.apply(t, k)));
        return list.stream();
    }

    public MapStream<T, K> filter(BiPredicate<T, K> predicate) {
        Map<T, K> map = new HashMap<>();
        this.map.forEach((t, k) -> {
            if (predicate.test(t, k)) {
                map.put(t, k);
            }
        });
        return of(map);
    }

    public <J> MapStream<T, J> mapValues(Function<K, J> mapper) {
        List<T> keys = this.map.keySet().stream().toList();
        List<J> values = this.map.values().stream().map(mapper).toList();
        return create(keys, values);
    }

    public <J> MapStream<J, K> mapKeys(Function<T, J> mapper) {
        List<J> keys = this.map.keySet().stream().map(mapper).toList();
        List<K> values = this.map.values().stream().toList();
        return create(keys, values);
    }

    public Map<T, K> toMap() {
        return Map.copyOf(this.map);
    }

    public <J, I> MapStream<J, I> biMap(BiFunction<T, K, StreamEntry<J, I>> mapper) {
        return of(map(mapper).toList());
    }

    public MapStream<T, K> forEach(BiConsumer<T, K> consumer) {
        this.map.forEach(consumer);
        return this;
    }

    private static <T, K> MapStream<T, K> of(List<StreamEntry<T, K>> list) {
        Map<T, K> map = new HashMap<>();
        list.forEach(entry -> {
            map.put(entry.t(), entry.k());
        });
        return of(map);
    }

    public boolean allMatch(BiPredicate<T, K> predicate) {
        int i = map.size();
        MapStream<T, K> filtered = filter(predicate);
        return i == filtered.map.size();
    }

    public boolean anyMatch(BiPredicate<T, K> predicate) {
        return filter(predicate).map.size() > 0;
    }

    public boolean noneMatch(BiPredicate<T, K> predicate) {
        return filter(predicate).map.size() == 0;
    }
}
