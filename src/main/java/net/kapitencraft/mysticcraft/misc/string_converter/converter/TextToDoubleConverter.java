package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import java.util.HashMap;
import java.util.function.Supplier;

public class TextToDoubleConverter extends TextToNumConverter<Double> {
    public TextToDoubleConverter(HashMap<String, Supplier<Double>> stringTransfers) {
        super(stringTransfers);
    }

    @Override
    protected Double createFromString(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            if (stringTransfers.containsKey(s)) {
                return stringTransfers.get(s).get();
            } else {
                throw new IllegalArgumentException("Unable to find argument: '" + s + "'");
            }
        }
    }
}
