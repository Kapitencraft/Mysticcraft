package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.misc.string_converter.args.BoolCalcArg;
import net.kapitencraft.mysticcraft.misc.string_converter.args.CalculationArgument;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TextToBoolConverter extends TextConverter<Boolean> {
    protected TextToBoolConverter(Map<String, Supplier<Boolean>> map) {
        super(map);
    }

    protected Boolean createFromString(String s) {
        if (stringTransfers.containsKey(s)) {
            return stringTransfers.get(s).get();
        } else {
            return Boolean.parseBoolean(s);
        }
    }

    @Override
    protected CalculationArgument<Boolean> getCalcArg(String value) {
        return new BoolCalcArg(value);
    }

    @Override
    protected boolean isArg(String s) {
        return PREFERRED_ARGS.contains(s) || OTHER_ARGS.contains(s);
    }

    public static List<String> PREFERRED_ARGS = List.of("==", "!=", "<=", ">=");
    public static List<String> OTHER_ARGS = List.of("||", "&&");
}
