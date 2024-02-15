package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.api.Reference;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorHelper {

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

    public static <L> Collector<L, ?, Integer> getBiggest(ToIntFunction<L> mapper) {
        return new CollectorImpl<L, Reference<Integer>, Integer>(Reference::new, (ref, l) -> {
            int i = mapper.applyAsInt(l);
            if (i > ref.getIntValue()) {
                ref.setValue(i);
            }
        }, (ref, ref1) -> ref.getIntValue() < ref1.getIntValue() ? ref.setValue(ref1.getValue()) : ref,
                Reference::getIntValue, Collections.emptySet()
        );
    }
}
