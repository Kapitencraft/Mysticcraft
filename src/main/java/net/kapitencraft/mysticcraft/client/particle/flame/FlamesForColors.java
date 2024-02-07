package net.kapitencraft.mysticcraft.client.particle.flame;

import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import org.joml.Vector3f;

public class FlamesForColors {

    public static final ParticleOptions RED = new FlameParticleOptions(new Vector3f(1, 0, 0));
    public static final ParticleOptions PURPLE = new FlameParticleOptions(new Vector3f(0.75f, 0.75f, 0));
    public static final ParticleOptions DARK_PURPLE_FLAME = new FlameParticleOptions(new Vector3f(0.4f, 0.4f, 0));
}