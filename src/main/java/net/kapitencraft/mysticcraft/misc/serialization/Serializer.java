package net.kapitencraft.mysticcraft.misc.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class Serializer<T, K extends DynamicOps<T>, L> {

    private final K generator;
    private final Codec<L> codec;
    private final Supplier<L> defaulted;
    public Serializer(K generator, Codec<L> codec, Supplier<L> defaulted) {
        this.generator = generator;
        this.codec = codec;
        this.defaulted = defaulted;
    }

    abstract T getSerializeDefault();

    public T serialize(@NotNull L value) {
        return TagHelper.get(codec.encodeStart(generator, value), this::getSerializeDefault);
    }

    public L deserialize(T object) {
        if (object == null) return defaulted.get();
        return TagHelper.get(codec.parse(generator, object), MiscHelper.nonNullOr(defaulted, ()-> null));
    }
}
