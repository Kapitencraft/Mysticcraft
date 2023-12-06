package net.kapitencraft.mysticcraft.api;

import java.util.Objects;

public interface SaveAbleEnum {
    String getName();

    @SafeVarargs
    static <T extends SaveAbleEnum> T getValue(T defaultValue, String key, T... values) {
        for (T t : values) {
            if (Objects.equals(t.getName(), key)) {
                return t;
            }
        }
        return defaultValue;
    }
}
