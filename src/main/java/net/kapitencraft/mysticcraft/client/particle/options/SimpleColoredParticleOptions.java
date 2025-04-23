package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.util.Color;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Function;

public abstract class SimpleColoredParticleOptions<T extends SimpleColoredParticleOptions<T>> extends ColoredParticleOptions<T> {

    public SimpleColoredParticleOptions(boolean p_123740_, ParticleOptions.Deserializer p_123741_, Color color) {
        super(p_123740_, p_123741_, color);
    }

    protected static <T extends SimpleColoredParticleOptions<T>> Codec<T> createSimpleCodec(Function<Color, T> constructor) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        Color.CODEC.fieldOf("color")
                                .forGetter(T::getColor)
                ).apply(instance, constructor)
        );
    }

    public Color getColor() {
        return color;
    }

    protected static class Deserializer<T extends SimpleColoredParticleOptions<T>> implements ParticleOptions.Deserializer<T> {
        private final Function<Color, T> mapper;
        private final Codec<T> codec;

        public Deserializer(Function<Color, T> mapper) {
            this.mapper = mapper;
            this.codec = createSimpleCodec(mapper);
        }

        public Codec<T> getCodec() {
            return codec;
        }

        @Override
        public @NotNull T fromCommand(@NotNull ParticleType<T> p_123733_, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f f = DustParticleOptionsBase.readVector3f(reader);
            Color color = new Color(f.x, f.y, f.z, 1);
            return mapper.apply(color);
        }

        @Override
        public @NotNull T fromNetwork(@NotNull ParticleType<T> p_123735_, @NotNull FriendlyByteBuf buf) {
            Color color = Color.read(buf);
            return mapper.apply(color);
        }
    }
}
