package net.kapitencraft.mysticcraft.misc.string_converter;

public record ValueArgument<T extends Number>(T value) implements TransferArg<T> {

    @Override
    public T apply(T a, T b) {
        return (T) (Number) 0;
    }

    public static <T extends Number> ValueArgument<T> create(MathArgument<T> mathArgument, ValueArgument<T> arg1, ValueArgument<T> arg2) {
        return new ValueArgument<>(mathArgument.apply(arg1.value, arg2.value));
    }
}