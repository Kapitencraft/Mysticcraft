package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class TextToNumConverter<T extends Number> extends TextConverter<T> {

    public TextToNumConverter(Map<String, Supplier<T>> stringTransfers) {
        super(stringTransfers);
    }


    @Override
    protected boolean isArg(String s) {
        return PREFERRED_ARGS.contains(s) || OTHER_ARGS.contains(s);
    }
    public static List<String> PREFERRED_ARGS = List.of("*", "/", "%");
    public static List<String> OTHER_ARGS = List.of("+", "-");
}