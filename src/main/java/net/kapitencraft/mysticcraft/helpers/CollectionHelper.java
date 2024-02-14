package net.kapitencraft.mysticcraft.helpers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class CollectionHelper {

    public static <T, K> T getFirstKey(Map<T, K> collection) {
        for (Map.Entry<T, K> entry : collection.entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    public static <T, K> Multimap<T, K> sortMap(Multimap<T, K> map, @Nullable Comparator<T> keySorter, @Nullable Comparator<K> valueSorter) {
        List<T> sortedKeys = fromAny(map.keySet());
        MiscHelper.ifNonNull(keySorter, sortedKeys::sort);
        Multimap<T, K> multimap = HashMultimap.create();
        sortedKeys.forEach(t -> {
            List<K> values = mutableList(fromAny(map.get(t)));
            MiscHelper.ifNonNull(valueSorter, values::sort);
            multimap.putAll(t, values);
        });
        return multimap;
    }

    public static <T> List<T> mutableList(List<T> immutable) {
        return new ArrayList<>(immutable);
    }

    public static <T> List<T> fromAny(Collection<T> collection) {
        return collection.stream().toList();
    }

    public static <T> void removeMapping2(List<T> ts, BiPredicate<T, T> predicate) {
        ts.removeIf(t -> {
            int id = ts.indexOf(t);
            T second;
            if (id < ts.size() - 1) {
                second = ts.get(id + 1);
            } else {
                second = ts.get(0);
            }
            return predicate.test(t, second);
        });
    }

    public static <T> void forEachMapping2(List<T> ts, BiConsumer<T, T> consumer) {
        ts.forEach(t -> {
            int id = ts.indexOf(t);
            T second;
            if (id < ts.size() - 1) {
                second = ts.get(id + 1);
            } else {
                second = ts.get(0);
            }
            consumer.accept(t, second);
        });
    }

    public static <T> List<T> create(int size, Supplier<T> sup) {
        List<T> list = new ArrayList<>();
        MiscHelper.repeat(size, integer -> list.add(sup.get()));
        return list;
    }

    public static <T, K, J> Function<K, J> biMap(T always, BiFunction<K, T, J> mapper) {
        return k -> mapper.apply(k, always);
    }

    public static <T, K, J> Function<K, J> reversedBiMap(T always, BiFunction<T, K, J> mapper) {
        return k -> mapper.apply(always, k);
    }

    public static <T, K> Predicate<T> biFilter(K always, BiPredicate<T, K> predicate) {
        return t -> predicate.test(t, always);
    }

    public static <T, K> Consumer<T> biUsage(K always, BiConsumer<T, K> consumer) {
        return t -> consumer.accept(t, always);
    }

    public static <T> ArrayList<T> toList(T ts) {
        ArrayList<T> target = new ArrayList<>();
        Collections.addAll(target, ts);
        return target;
    }

    private static class CollectorImpl<T, L, K> implements Collector<T, L, K> {
        private final Supplier<L> supplier;
        private final BiConsumer<L, T> accumulator;
        private final BinaryOperator<L> combiner;
        private final Function<L, K> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<L> supplier, BiConsumer<L, T> accumulator, BinaryOperator<L> combiner, Function<L, K> finisher, Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        @Override
        public Supplier<L> supplier() {
            return supplier;
        }

        @Override
        public BiConsumer<L, T> accumulator() {
            return accumulator;
        }

        @Override
        public BinaryOperator<L> combiner() {
            return combiner;
        }

        @Override
        public Function<L, K> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    public static <T, K> Multimap<T, K> fromMap(Map<T, K> map) {
        Multimap<T, K> multimap = HashMultimap.create();
        for (T t : map.keySet()) {
            multimap.put(t, map.get(t));
        }
        return multimap;
    }
    public static <T> Collector<Collection<T>, List<T>, Stream<T>> merge() {
        return new CollectorImpl<>(ArrayList::new, List::addAll, (list, list2) -> {
            list.addAll(list2);
            return list;
        }, List::stream, Collections.emptySet());
    }

    public static <T, J, K> Collector<T, ?, MapStream<J, K>> toMapStream(Function<T, J> keyMapper, Function<T, K> valueMapper) {
        return copyWithFinish(Collectors.toMap(keyMapper, valueMapper), MapStream::of);
    }

    public static <A, B, C, D> Collector<A, B, D> copyWithFinish(Collector<A, B, C> collector, Function<C, D> finish) {
        return new CollectorImpl<>(collector.supplier(), collector.accumulator(), collector.combiner(), b -> finish.apply(collector.finisher().apply(b)) , collector.characteristics());
    }

    public static <T, K, L, J extends Map<K, L>> List<L> values(Map<T, J> map) {
        return map.values().stream().map(Map::values).collect(merge()).toList();
    }

    public static <T, L> Collector<T, ?, Map<T, L>> createMap(Function<T, L> valueMapper) {
        return Collectors.toMap(t -> t, valueMapper);
    }

    public static <T, L> Collector<L, ?, Map<T, L>> createMapForKeys(Function<L, T> keyMapper) {
        return Collectors.toMap(keyMapper, t-> t);
    }

    public static <T, K> K getFirstValue(Map<T, K> map) {
        for (Map.Entry<T, K> entry : map.entrySet()) {
            return entry.getValue();
        }
        return null;
    }

    public static <T, K> void removeIf(Map<T, K> map, Predicate<T> predicate) {
        for (T t : map.keySet()) {
            if (predicate.test(t)) {
                map.remove(t);
            }
        }
    }

    public static <T, V> void forEach(HashMap<T, HashMap<T, V>> map, BiConsumer<? super T, ? super V> consumer) {
        for (HashMap<T, V> map1 : map.values()) {
            map1.forEach(consumer);
        }
    }

    public static  <V> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i > 0; i--) {

            out.add(list.get((i - 1)));
        }
        return out;
    }

    public static List<LivingEntity> sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return List.of();
        }
        return list.stream().sorted(Comparator.comparingDouble(living -> living.distanceToSqr(source.position()))).collect(Collectors.toList());
    }

    public static <T> boolean arrayContains(T[] array, T t) {
        return List.of(array).contains(t);
    }

    public static <T> T[] listToArray(List<T> list) {
        return (T[]) list.toArray();
    }

    public static <T> List<T> copy(T[] source) {
        return Arrays.asList(source);
    }

    public static <T> List<T> remove(T[] values, T... toRemove) {
        return Arrays.stream(values).filter(t -> !arrayContains(toRemove, t)).toList();
    }

    public static <T> List<T> add(T[] values, T... toAdd) {
        List<T> source = new ArrayList<>(Arrays.stream(values).toList());
        source.addAll(Arrays.stream(toAdd).toList());
        return source;
    }
}
