package net.kapitencraft.mysticcraft.mixin;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MixinUtils {

    public static void add(Supplier<Integer> getter, Consumer<Integer> setter, int change) {
        setter.accept(getter.get() + change);
    }
}
