package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.misc.string_converter.args.CalculationArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.args.MathArgument;

import java.util.Map;
import java.util.function.Supplier;

public class TextToIntConverter extends TextToNumConverter<Integer> {

    public TextToIntConverter(Map<String, Supplier<Integer>> stringTransfers) {
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

    @Override
    protected CalculationArgument<Integer> getCalcArg(String value) {
        return new MathArgument<>(value);
    }


}