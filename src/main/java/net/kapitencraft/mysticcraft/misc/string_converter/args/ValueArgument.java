package net.kapitencraft.mysticcraft.misc.string_converter.args;

public record ValueArgument<T>(T value) implements TransferArg<T> {

    public static <T> ValueArgument<T> create(CalculationArgument<T> calcArg, ValueArgument<T> arg1, ValueArgument<T> arg2) {
        return new ValueArgument<>(calcArg.provide(arg1.value, arg2.value));
    }

    @Override
    public T provide(T value1, T value2) {
        return null;
    }
}