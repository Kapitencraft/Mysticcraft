package net.kapitencraft.mysticcraft.client.particle.flame;

import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.minecraft.core.particles.ParticleOptions;

public interface FlamesForColors {
    ParticleOptions RED = new FlameParticleOptions(new Color(1, 0, 0, 1));
    ParticleOptions GREEN = new FlameParticleOptions(new Color(0, 1, 0, 1));
    ParticleOptions BLUE = new FlameParticleOptions(new Color(0, 0, 1, 1));
    ParticleOptions PURPLE = new FlameParticleOptions(new Color(0.75f, 0, 0.75f, 1));
    ParticleOptions DARK_PURPLE_FLAME = new FlameParticleOptions(new Color(0.4f, 0, 0.4f, 1));
    ParticleOptions YELLOW = new FlameParticleOptions(new Color(0.75f, 0.75f, 0, 1));
    ParticleOptions ORANGE = new FlameParticleOptions(Color.fromARGBPacked(0xFFFFA700));
}