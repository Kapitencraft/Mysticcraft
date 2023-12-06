package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.api.SingleBinaryProvider;

public interface TransferArg<T> extends SingleBinaryProvider<T, T> {
    default T value() {
        return (T) (Number) 0;
    }
}