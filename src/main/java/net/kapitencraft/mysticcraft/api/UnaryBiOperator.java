package net.kapitencraft.mysticcraft.api;

import java.util.function.BiFunction;

@FunctionalInterface
public interface UnaryBiOperator<T> extends BiFunction<T, T, T> {
}
