package net.kapitencraft.mysticcraft.misc.string_converter.converter;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.string_converter.MathArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.TransferArg;
import net.kapitencraft.mysticcraft.misc.string_converter.ValueArgument;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class TextToNumConverter<T extends Number> extends TextConverter<T> {

    public TextToNumConverter(Map<String, Supplier<T>> stringTransfers) {
        super(stringTransfers);
    }

    public T transfer(String s) {
        String[] toTransfer = s.split(" ");
        List<TransferArg<T>> args = new ArrayList<>();
        for (String value : toTransfer) {
            if (isArg(value)) {
                args.add(new MathArgument<>(value));
            } else {
                args.add(new ValueArgument<>(createFromString(value)));
            }
        }
        List<TransferArg<T>> currentArgs = copyOf(args);
        args.clear();
        int cycle = 0;
        while (currentArgs.size() > 1 && cycle < 1000) {
            MathArgument<T> toMerge = getNextArg(currentArgs);
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
        T value = currentArgs.get(0).value();
        MysticcraftMod.sendInfo("sending new Value: " + value + " from args: " + s);
        return value;
    }

    protected abstract T createFromString(String s);

    protected static <T extends Number> @Nullable MathArgument<T> getNextArg(List<TransferArg<T>> list) {
        MathArgument<T> firstArg = null;
        for (TransferArg<T> transferArg : list) {
            if (transferArg instanceof MathArgument<T> mathArgument) {
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


    protected static <T> ArrayList<T> copyOf(List<T> list) {
        return new ArrayList<>(list);
    }


    protected static <T> void removeAll(List<T> list, T... ts) {
        for (T t : ts) {
            list.remove(t);
        }
    }


    protected boolean isArg(String s) {return POSSIBLE_ARGS.contains(s);}

    protected static final List<String> POSSIBLE_ARGS = List.of("+", "-", "*", "/", "%");
}