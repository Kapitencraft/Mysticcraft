package net.kapitencraft.mysticcraft.misc.string_converter.args;

import net.kapitencraft.mysticcraft.api.UnaryBiOperator;

public interface CalculationArgument<T> extends TransferArg<T>, UnaryBiOperator<T> {
    boolean isPreferred();
}
