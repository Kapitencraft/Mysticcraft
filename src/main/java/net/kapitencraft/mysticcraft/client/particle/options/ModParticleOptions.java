package net.kapitencraft.mysticcraft.client.particle.options;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public abstract class ModParticleOptions<T extends ModParticleOptions<T>> extends ParticleType<T> implements ParticleOptions {
    public ModParticleOptions(boolean p_123740_) {
        super(p_123740_);
    }
}
