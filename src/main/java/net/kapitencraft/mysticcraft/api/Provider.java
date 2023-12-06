package net.kapitencraft.mysticcraft.api;

public interface Provider<T, K> {
    T provide(K value);
}