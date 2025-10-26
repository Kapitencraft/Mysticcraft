package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class CircleParticleOptions extends SimpleColoredParticleOptions<CircleParticleOptions> {
    private static final MapCodec<CircleParticleOptions> CODEC = RecordCodecBuilder.mapCodec(optionsInstance ->
            optionsInstance.group(
                Color.CODEC.fieldOf("color")
                    .forGetter(CircleParticleOptions::getColor),
                Codec.DOUBLE.fieldOf("size")
                    .forGetter(CircleParticleOptions::getSize),
                Codec.DOUBLE.fieldOf("expandSpeed")
                    .forGetter(CircleParticleOptions::getExpandSpeed)
            ).apply(optionsInstance, CircleParticleOptions::new));

    public static final StreamCodec<? super RegistryFriendlyByteBuf, CircleParticleOptions> STREAM_CODEC = StreamCodec.composite(
            Color.STREAM_CODEC, CircleParticleOptions::getColor,
            ByteBufCodecs.DOUBLE, CircleParticleOptions::getSize,
            ByteBufCodecs.DOUBLE, CircleParticleOptions::getExpandSpeed,
            CircleParticleOptions::new
    );

    private final double size;
    private final double expandSpeed;

    public CircleParticleOptions(Color color, double size, double expandSpeed) {
        super(true, color);
        this.size = size;
        this.expandSpeed = expandSpeed;
    }

    public double getExpandSpeed() {
        return expandSpeed;
    }

    public double getSize() {
        return size;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticleTypes.CIRCLE.get();
    }

    @Override
    public @NotNull MapCodec<CircleParticleOptions> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, CircleParticleOptions> streamCodec() {
        return STREAM_CODEC;
    }
}
