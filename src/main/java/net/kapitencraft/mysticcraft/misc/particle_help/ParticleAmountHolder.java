package net.kapitencraft.mysticcraft.misc.particle_help;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public record ParticleAmountHolder(ParticleType<SimpleParticleType> particleType, int amount) {
}
