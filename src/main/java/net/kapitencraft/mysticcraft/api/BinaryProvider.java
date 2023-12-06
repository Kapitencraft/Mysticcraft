package net.kapitencraft.mysticcraft.api;

public interface BinaryProvider<T, K, L> {
    T provide(K value1, L value2);
}