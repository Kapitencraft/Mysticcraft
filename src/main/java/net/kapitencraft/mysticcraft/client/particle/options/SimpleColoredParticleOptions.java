package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.client.render.ColorAnimator;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Function;

public abstract class SimpleColoredParticleOptions<T extends SimpleColoredParticleOptions<T>> extends ColoredParticleOptions<T> {

    public SimpleColoredParticleOptions(boolean p_123740_, ParticleOptions.Deserializer p_123741_, Vector3f color) {
        super(p_123740_, p_123741_, ColorAnimator.simple(color));
    }

    protected static <T extends SimpleColoredParticleOptions<T>> Codec<T> createSimpleCodec(Function<Vector3f, T> constructor) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        ExtraCodecs.VECTOR3F.fieldOf("color")
                                .forGetter(T::getColor)
                ).apply(instance, constructor)
        );
    }

    public Vector3f getColor() {
        return animator.getAsSimple();
    }

    protected static class Deserializer<T extends SimpleColoredParticleOptions<T>> implements ParticleOptions.Deserializer<T> {
        private final Function<Vector3f, T> mapper;
        private final Codec<T> codec;

        public Deserializer(Function<Vector3f, T> mapper) {
            this.mapper = mapper;
            this.codec = createSimpleCodec(mapper);
        }

        public Codec<T> getCodec() {
            return codec;
        }

        @Override
        public @NotNull T fromCommand(@NotNull ParticleType<T> p_123733_, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = DustParticleOptionsBase.readVector3f(reader);
            return mapper.apply(color);
        }

        @Override
        public @NotNull T fromNetwork(@NotNull ParticleType<T> p_123735_, @NotNull FriendlyByteBuf buf) {
            Vector3f color = DustParticleOptionsBase.readVector3f(buf);
            return mapper.apply(color);
        }
    }
}
