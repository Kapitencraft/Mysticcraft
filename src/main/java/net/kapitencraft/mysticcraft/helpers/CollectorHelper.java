package net.kapitencraft.mysticcraft.helpers;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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
        return Collector.of(ArrayList::new, List::addAll, (list, list2) -> {
            list.addAll(list2);
            return list;
        }, List::stream);
    }

    public static <T, L> Collector<T, ?, Map<T, L>> createMap(Function<T, L> valueMapper) {
        return Collectors.toMap(t -> t, valueMapper);
    }

    public static <T, L> Collector<L, ?, Map<T, L>> createMapForKeys(Function<L, T> keyMapper) {
        return Collectors.toMap(keyMapper, t-> t);
    }

    public static Collector<Component, MutableComponent, Component> joinComponent(Component filler) {
        return Collector.of(Component::empty, MutableComponent::append, (component, component2) -> {
            component.append(filler).append(component2);
            return component;
        }, Component::copy);
    }
}
