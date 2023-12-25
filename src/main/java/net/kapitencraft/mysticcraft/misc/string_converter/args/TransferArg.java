package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.api.UnaryBiOperator;

public interface TransferArg<T> extends UnaryBiOperator<T> {
    default T value() {
        return (T) (Number) 0;
    }
}