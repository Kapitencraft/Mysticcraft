package net.kapitencraft.mysticcraft.misc.function;

public interface BinaryProvider<T, K, L> {
    T provide(K value1, L value2);
}