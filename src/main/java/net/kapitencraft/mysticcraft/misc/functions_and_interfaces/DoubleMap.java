package net.kapitencraft.mysticcraft.misc.functions_and_interfaces;

import java.util.HashMap;
import java.util.function.Consumer;

public class DoubleMap<T, K, L> extends HashMap<T, HashMap<K, L>> {

    public void forValues(Consumer<L> consumer) {

    }


    public L getOrAdd(T t, K k, L ifAbsent) {
        if (this.containsKey(t)) {
            this.get(t).putIfAbsent(k, ifAbsent);
        } else {
            this.put(t, new HashMap<>());
            this.get(t).put(k, ifAbsent);
        }
        return get(t, k);
    }

    public L get(T t, K k) {
        return this.get(t).get(k);
    }
}
