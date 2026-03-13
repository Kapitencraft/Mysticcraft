package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.kap_lib.core.util.Color;
import net.kapitencraft.mysticcraft.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class FlameParticleOptions extends SimpleColoredParticleOptions<FlameParticleOptions> {
    public static final MapCodec<FlameParticleOptions> CODEC = createCodec(FlameParticleOptions::new);
    public static final StreamCodec<? super RegistryFriendlyByteBuf, FlameParticleOptions> STREAM_CODEC = createStreamCodec(FlameParticleOptions::new);

    public FlameParticleOptions(Color color) {
        super(true, color);
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticleTypes.FLAME.get();
    }

    @Override
    public @NotNull MapCodec<FlameParticleOptions> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, FlameParticleOptions> streamCodec() {
        return STREAM_CODEC;
    }
}
