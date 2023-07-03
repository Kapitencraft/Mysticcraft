package net.kapitencraft.mysticcraft.misc.string_converter;

public interface TransferArg<T extends Number> {
    T apply(T a, T b);

    default T value() {
        return (T) (Number) 0;
    }
}