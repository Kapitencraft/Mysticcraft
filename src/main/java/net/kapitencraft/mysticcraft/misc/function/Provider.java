package net.kapitencraft.mysticcraft.misc.function;

public interface Provider<T, K> {

    T provide(K value);
}