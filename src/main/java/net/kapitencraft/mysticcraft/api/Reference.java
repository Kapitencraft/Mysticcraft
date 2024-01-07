package net.kapitencraft.mysticcraft.api;

import org.jetbrains.annotations.Nullable;

public class Reference<T> {

    private T value;

    private Reference(@Nullable T defaultValue) {
        value = defaultValue;
    }

    public static <T> Reference<T> of(@Nullable T t) {
        return new Reference<>(t);
    }

    public Reference<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public T getValue() {
        return value;
    }
}
