package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

import java.util.Objects;

public interface SaveAbleEnum {
    String getName();

    static <T extends SaveAbleEnum> T getValue(T defaultValue, String key, T... values) {
        for (T t : values) {
            if (Objects.equals(t.getName(), key)) {
                return t;
            }
        }
        return defaultValue;
    }
}
