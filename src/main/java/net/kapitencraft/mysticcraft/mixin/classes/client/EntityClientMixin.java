package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.mysticcraft.misc.particle_help.animation.IAnimatable;
import net.kapitencraft.mysticcraft.misc.particle_help.animation.ParticleAnimator;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityClientMixin implements IAnimatable {
    private final ParticleAnimator animator = new ParticleAnimator(own());

    private Entity own() {
        return (Entity) (Object) this;
    }

    @Override
    public ParticleAnimator getAnimator() {
        return animator;
    }
}
