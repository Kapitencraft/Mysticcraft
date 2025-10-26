package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.kap_lib.util.Color;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public abstract class ColoredParticleOptions<T extends ColoredParticleOptions<T>> extends ModParticleOptions<T> {
    public static <T extends SimpleColoredParticleOptions<T>> MapCodec<T> createCodec(Function<Color, T> function) {
        return Color.CODEC.xmap(function, SimpleColoredParticleOptions::getColor).fieldOf("color");
    }
    public static <T extends SimpleColoredParticleOptions<T>> StreamCodec<? super RegistryFriendlyByteBuf, T> createStreamCodec(Function<Color, T> function) {
        return Color.STREAM_CODEC.map(function, SimpleColoredParticleOptions::getColor);
    }

    protected final Color color;

    public ColoredParticleOptions(boolean p_123740_, Color color) {
        super(p_123740_);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
