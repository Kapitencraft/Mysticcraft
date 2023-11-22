package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SingleBinaryProvider;
import net.kapitencraft.mysticcraft.misc.string_converter.converter.TextToNumConverter;

public class MathArgument<T extends Number> implements CalculationArgument<T> {
    private final String name;

    public MathArgument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public T provide(T a, T b) {
        return switch (name) {
            case "*" -> MULTIPLY.provide(a, b);
            case "/" -> DIVIDE.provide(a, b);
            case "+" -> ADD.provide(a, b);
            case "-" -> REDUCE.provide(a, b);
            case "%" -> MODULO.provide(a, b);
            default -> (T) (Number) 0;
        };
    }

    private final SingleBinaryProvider<T, T> MULTIPLY = (value1, value2) -> (T) (Number) (((Double) value1) * ((Double) value1));
    private final SingleBinaryProvider<T, T> DIVIDE = (value1, value2) -> (T) (Number) (((Double) value1) / ((Double) value2));
    private final SingleBinaryProvider<T, T> ADD = (value1, value2) -> (T) (Number) (((Double) value1) + ((Double) value2));
    private final SingleBinaryProvider<T, T> REDUCE = (value1, value2) -> (T) (Number) (((Double) value1) - ((Double) value2));
    private final SingleBinaryProvider<T, T> MODULO = (value1, value2) -> (T) (Number) (((Integer) value1) % ((Integer) value2));

    @Override
    public boolean isPreferred() {
        return TextToNumConverter.PREFERRED_ARGS.contains(name);
    }
}