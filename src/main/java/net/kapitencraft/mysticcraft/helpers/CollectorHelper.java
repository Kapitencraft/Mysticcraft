package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.api.MapStream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorHelper {

    private record CollectorImpl<T, L, K>(Supplier<L> supplier, BiConsumer<L, T> accumulator,
                                          BinaryOperator<L> combiner, Function<L, K> finisher,
                                          Set<Characteristics> characteristics) implements Collector<T, L, K> {
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

    public static <T, L> Collector<T, ?, Map<T, L>> createMap(Function<T, L> valueMapper) {
        return Collectors.toMap(t -> t, valueMapper);
    }

    public static <T, L> Collector<L, ?, Map<T, L>> createMapForKeys(Function<L, T> keyMapper) {
        return Collectors.toMap(keyMapper, t-> t);
    }
}
