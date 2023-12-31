package net.kapitencraft.mysticcraft.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Collector<T> implements Supplier<List<T>> {

    boolean collecting = false;
    List<T> list = new ArrayList<>();

    @Override
    public List<T> get() {
        List<T> copy = Collections.unmodifiableList(list);
        reset();
        return copy;
    }

    public void start() {
        collecting = true;
    }

    public void reset() {
        collecting = false;
        list.clear();
    }
}
