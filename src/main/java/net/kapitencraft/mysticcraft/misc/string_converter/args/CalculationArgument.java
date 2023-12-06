package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.api.SingleBinaryProvider;

public interface CalculationArgument<T> extends TransferArg<T>, SingleBinaryProvider<T, T> {
    boolean isPreferred();
}
