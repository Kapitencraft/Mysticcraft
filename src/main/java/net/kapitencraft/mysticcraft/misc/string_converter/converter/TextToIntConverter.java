package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import java.util.HashMap;
import java.util.function.Supplier;

public class TextToIntConverter extends TextToNumConverter<Integer> {

    public TextToIntConverter(HashMap<String, Supplier<Integer>> stringTransfers) {
        super(stringTransfers);
    }

    @Override
    protected Integer createFromString(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            if (stringTransfers.containsKey(s)) {
                return stringTransfers.get(s).get();
            } else {
                throw new IllegalArgumentException("Unable to find argument: '" + s + "'");
            }
        }
    }


}