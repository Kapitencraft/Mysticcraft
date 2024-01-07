package net.kapitencraft.mysticcraft.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Counter<T> implements Supplier<List<T>> {

    boolean counting = false;
    List<T> list = new ArrayList<>();

    @Override
    public List<T> get() {
        List<T> copy = Collections.unmodifiableList(list);
        reset();
        return copy;
    }

    public void start() {
        counting = true;
    }

    public void reset() {
        counting = false;
        list.clear();
    }
}
