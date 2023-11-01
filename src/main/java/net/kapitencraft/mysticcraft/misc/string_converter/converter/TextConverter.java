package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import java.util.Map;
import java.util.function.Supplier;

public class TextConverter<T> {
    protected final Map<String, Supplier<T>> stringTransfers;

    protected TextConverter(Map<String, Supplier<T>> map) {
        this.stringTransfers = map;
    }
}
