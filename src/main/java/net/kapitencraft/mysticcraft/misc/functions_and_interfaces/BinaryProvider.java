package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

public interface BinaryProvider<T, K, L> {
    T provide(K value1, L value2);
}