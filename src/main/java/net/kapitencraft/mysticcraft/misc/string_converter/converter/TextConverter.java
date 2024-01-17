package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.string_converter.args.CalculationArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.args.TransferArg;
import net.kapitencraft.mysticcraft.misc.string_converter.args.ValueArgument;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class TextConverter<T> {
    protected final Map<String, Supplier<T>> stringTransfers;

    protected TextConverter(Map<String, Supplier<T>> map) {
        this.stringTransfers = map;
    }

    protected abstract T createFromString(String s);

    protected abstract CalculationArgument<T> getCalcArg(String value);

    public T transfer(String s) {
        String[] toTransfer = s.split(" ");
        List<TransferArg<T>> args = new ArrayList<>();
        for (String value : toTransfer) {
            if (isArg(value)) {
                args.add(getCalcArg(value));
            } else {
                args.add(new ValueArgument<>(createFromString(value)));
            }
        }
        List<TransferArg<T>> currentArgs = copyOf(args);
        args.clear();
        int cycle = 0;
        while (currentArgs.size() > 1 && cycle < 1000) {
            CalculationArgument<T> toMerge = getNextArg(currentArgs);
            if (toMerge == null) {
                break;
            } else {
                int pos = currentArgs.indexOf(toMerge);
                ValueArgument<T> firstArg = (ValueArgument<T>) currentArgs.get(pos-1);
                ValueArgument<T> secondArg = (ValueArgument<T>) currentArgs.get(pos+1);
                ValueArgument<T> resultArg = ValueArgument.create(toMerge, firstArg, secondArg);
                removeAll(currentArgs, toMerge, firstArg, secondArg);
                currentArgs.add(pos-1, resultArg);
            }
            cycle++;
        }
        if (cycle == 1000) MysticcraftMod.LOGGER.warn("the converter failed to convert value from args: {}; out of time", s);
        return currentArgs.get(0).value();
    }

    protected abstract boolean isArg(String s);


    protected static <T> @Nullable CalculationArgument<T> getNextArg(List<TransferArg<T>> list) {
        CalculationArgument<T> firstArg = null;
        for (TransferArg<T> transferArg : list) {
            if (transferArg instanceof CalculationArgument<T> mathArgument) {
                if (mathArgument.isPreferred()){
                    return mathArgument;
                } else {
                    if (firstArg == null) {
                        firstArg = mathArgument;
                    }
                }
            }
        }
        return firstArg;
    }

    protected static <T> void removeAll(List<T> list, T... ts) {
        for (T t : ts) {
            list.remove(t);
        }
    }

    protected static <T> ArrayList<T> copyOf(List<T> list) {
        return new ArrayList<>(list);
    }
}
