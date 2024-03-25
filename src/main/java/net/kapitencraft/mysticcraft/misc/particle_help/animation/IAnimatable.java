package net.kapitencraft.mysticcraft.misc.particle_help.animation;

import net.minecraft.world.entity.Entity;

public interface IAnimatable {
    ParticleAnimator getAnimator();

    static ParticleAnimator get(Entity entity) {
        return ((IAnimatable) entity).getAnimator();
    }
}
