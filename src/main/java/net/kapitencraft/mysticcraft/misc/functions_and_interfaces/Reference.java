package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

import org.jetbrains.annotations.Nullable;

public class Reference<T> {

    private T value = null;

    private Reference(T defaultValue) {
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
