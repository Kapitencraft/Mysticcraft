package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.misc.string_converter.args.CalculationArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.args.MathArgument;

public class TextToDoubleConverter extends TextToNumConverter<Double> {
    public TextToDoubleConverter(String args) {
        super(args, Double::valueOf);
    }

    @Override
    protected CalculationArgument<Double> getCalcArg(String value) {
        return new MathArgument<>(value);
    }
}
