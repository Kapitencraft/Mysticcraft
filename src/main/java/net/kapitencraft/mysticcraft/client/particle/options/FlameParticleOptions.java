package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.registry.ModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class FlameParticleOptions extends SimpleColoredParticleOptions<FlameParticleOptions> {
    private static final Deserializer<FlameParticleOptions> DESERIALIZER = new Deserializer<>(FlameParticleOptions::new);

    public FlameParticleOptions(Color color) {
        super(true, DESERIALIZER, color);
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticleTypes.FLAME.get();
    }

    @Override
    public @NotNull Codec<FlameParticleOptions> codec() {
        return DESERIALIZER.getCodec();
    }
}
