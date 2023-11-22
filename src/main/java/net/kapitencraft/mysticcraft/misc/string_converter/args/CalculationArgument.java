package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SingleBinaryProvider;

public interface CalculationArgument<T> extends TransferArg<T>, SingleBinaryProvider<T, T> {
    boolean isPreferred();
}
