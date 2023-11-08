package net.kapitencraft.mysticcraft.misc.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import org.jetbrains.annotations.NotNull;

public abstract class Serializer<T, K extends DynamicOps<T>, L> {

    private final K generator;
    private final Codec<L> codec;
    private final L defaulted;
    public Serializer(K generator, Codec<L> codec, L defaulted) {
        this.generator = generator;
        this.codec = codec;
        this.defaulted = defaulted;
    }

    abstract T getSerializeDefault();

    public T serialize(@NotNull L value) {
        return TagHelper.getOrLog(codec.encodeStart(generator, value), getSerializeDefault());
    }

    public @NotNull L deserialize(@NotNull T object) {
        return TagHelper.getOrLog(codec.parse(generator, object), defaulted);
    }
}
