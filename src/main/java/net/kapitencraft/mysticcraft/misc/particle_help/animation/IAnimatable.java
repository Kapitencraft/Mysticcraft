package net.kapitencraft.mysticcraft.misc.particle_help.animation;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IAnimatable {
    ParticleAnimator getAnimator();

    static ParticleAnimator get(Entity entity) {
        return ((IAnimatable) entity).getAnimator();
    }
}
