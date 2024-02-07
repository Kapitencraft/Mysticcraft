package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class FlameParticleOptions extends SimpleColoredParticleOptions<FlameParticleOptions> {
    private static final Deserializer<FlameParticleOptions> DESERIALIZER = new Deserializer<>(FlameParticleOptions::new);

    public FlameParticleOptions(Vector3f color) {
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
