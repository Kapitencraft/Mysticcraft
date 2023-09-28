package net.kapitencraft.mysticcraft.misc.string_converter;

import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SingleBinaryProvider;

public class MathArgument<T extends Number> implements SingleBinaryProvider<T, T>, TransferArg<T> {
    private final String name;

    public MathArgument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isPreferred() {
        return name != null && (name.equals("*") || name.equals("/") || name.equals("%"));
    }

    @Override
    public T provide(T a, T b) {
        if (a instanceof Integer) {
            return switch (name) {
                case "*" -> MULTIPLY_INTEGER.provide(a, b);
                case "/" -> DIVIDE_INTEGER.provide(a, b);
                case "+" -> ADD_INTEGER.provide(a, b);
                case "-" -> REDUCE_INTEGER.provide(a, b);
                case "%" -> MODULO_INTEGER.provide(a, b);
                default -> (T) (Number) 0;
            };
        } else {
            return switch (name) {
                case "*" -> MULTIPLY_DOUBLE.provide(a, b);
                case "/" -> DIVIDE_DOUBLE.provide(a, b);
                case "+" -> ADD_DOUBLE.provide(a, b);
                case "-" -> REDUCE_DOUBLE.provide(a, b);
                case "%" -> MODULO_DOUBLE.provide(a, b);
                default -> (T) (Number) 0;
            };
        }
    }

    private final SingleBinaryProvider<T, T> MULTIPLY_INTEGER = (value1, value2) -> (T) (Number) (((Integer) value1) * ((Integer) value1));
    private final SingleBinaryProvider<T, T> DIVIDE_INTEGER = (value1, value2) -> (T) (Number) (((Integer) value1) / ((Integer) value2));
    private final SingleBinaryProvider<T, T> ADD_INTEGER = (value1, value2) -> (T) (Number) (((Integer) value1) + ((Integer) value2));
    private final SingleBinaryProvider<T, T> REDUCE_INTEGER = (value1, value2) -> (T) (Number) (((Integer) value1) - ((Integer) value2));
    private final SingleBinaryProvider<T, T> MODULO_INTEGER = (value1, value2) -> (T) (Number) (((Integer) value1) % ((Integer) value2));

    private final SingleBinaryProvider<T, T> MULTIPLY_DOUBLE = (value1, value2) -> (T) (Number) (((Double) value1) * ((Double) value1));
    private final SingleBinaryProvider<T, T> DIVIDE_DOUBLE = (value1, value2) -> (T) (Number) (((Double) value1) / ((Double) value2));
    private final SingleBinaryProvider<T, T> ADD_DOUBLE = (value1, value2) -> (T) (Number) (((Double) value1) + ((Double) value2));
    private final SingleBinaryProvider<T, T> REDUCE_DOUBLE = (value1, value2) -> (T) (Number) (((Double) value1) - ((Double) value2));
    private final SingleBinaryProvider<T, T> MODULO_DOUBLE = (value1, value2) -> (T) (Number) (((Double) value1) % ((Double) value2));


    @Override
    public T apply(T a, T b) {
        return provide(a, b);
    }
}