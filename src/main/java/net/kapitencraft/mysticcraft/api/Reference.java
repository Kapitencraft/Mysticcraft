package net.kapitencraft.mysticcraft.api;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import org.jetbrains.annotations.Nullable;

import java.sql.Ref;

/**
 * type of variable able to be used from inside lambda
 */
public class Reference<T> {

    private T value;

    private Reference(@Nullable T defaultValue) {
        value = defaultValue;
    }

    public Reference() {}

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

    public int getIntValue() {
        if (value == null) {
            return 0;
        }
        try {
            return (int) value;
        } catch (Exception e) {
            MysticcraftMod.LOGGER.warn("error whilst attempting to get value: {}", e.getMessage());
        }
        return 0;
    }
}
