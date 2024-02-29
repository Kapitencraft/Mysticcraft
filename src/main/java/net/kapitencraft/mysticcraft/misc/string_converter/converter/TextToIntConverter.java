package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.misc.string_converter.args.CalculationArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.args.MathArgument;

public class TextToIntConverter extends TextToNumConverter<Integer> {

    public TextToIntConverter(String args) {
        super(args, Integer::valueOf);
    }

    @Override
    protected CalculationArgument<Integer> getCalcArg(String value) {
        return new MathArgument<>(value);
    }
}