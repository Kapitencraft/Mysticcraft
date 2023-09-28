package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

public interface Provider<T, K> {
    T provide(K value);
}