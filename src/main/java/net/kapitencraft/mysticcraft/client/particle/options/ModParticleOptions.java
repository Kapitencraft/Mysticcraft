package net.kapitencraft.mysticcraft.client.particle.options;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

public abstract class ModParticleOptions<T extends ModParticleOptions<T>> extends ParticleType<T> implements ParticleOptions {
    public ModParticleOptions(boolean p_123740_, ParticleOptions.Deserializer<T> p_123741_) {
        super(p_123740_, p_123741_);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()));
    }
}
