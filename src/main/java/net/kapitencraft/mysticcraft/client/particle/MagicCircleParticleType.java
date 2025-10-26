package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class MagicCircleParticleType extends ParticleType<MagicCircleParticleType> implements ParticleOptions {
    public static final MapCodec<MagicCircleParticleType> CODEC = Codec.INT.xmap(MagicCircleParticleType::new, MagicCircleParticleType::getLiving).fieldOf("living");
    public static final StreamCodec<ByteBuf, MagicCircleParticleType> STREAM_CODEC = ByteBufCodecs.INT.map(MagicCircleParticleType::new, MagicCircleParticleType::getLiving);

    private final int living;
    public MagicCircleParticleType(int living) {
        super(false);
        this.living = living;
    }

    public int getLiving() {
        return living;
    }

    @Override
    public @NotNull MagicCircleParticleType getType() {
        return this;
    }

    @Override
    public @NotNull MapCodec<MagicCircleParticleType> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, MagicCircleParticleType> streamCodec() {
        return STREAM_CODEC;
    }
}
