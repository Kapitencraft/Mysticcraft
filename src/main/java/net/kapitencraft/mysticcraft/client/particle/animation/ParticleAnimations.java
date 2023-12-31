package net.kapitencraft.mysticcraft.client.particle.animation;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public enum ParticleAnimations implements Predicate<ParticleAnimationParameters>, BiConsumer<Particle, ParticleAnimationParameters> {
    MOVE_AWAY(ParticleAnimParams.SOURCE),
    MOVE_TO(ParticleAnimParams.TARGET),
    MOVE(ParticleAnimParams.DELTA),
    ROTATE(ParticleAnimParams.SOURCE, ParticleAnimParams.ROTATION);


    private final ParticleAnimParam<?>[] requiredParams;

    ParticleAnimations(ParticleAnimParam<?>... requiredParams) {
        this.requiredParams = requiredParams;
    }

    @Override
    public boolean test(ParticleAnimationParameters particleAnimationContext) {
        return particleAnimationContext.containsAll(requiredParams);
    }

    @SuppressWarnings("all")
    @Override
    public void accept(Particle particle, ParticleAnimationParameters params) {
        ParticleMoveControl control = ParticleMoveControl.fromParticle(particle);
        int remaining = params.remainingAnimTime;
        switch (this) {
            case MOVE -> {
                Vec3 moveVec = params.getParam(ParticleAnimParams.DELTA).orElse(Vec3.ZERO);
                control.deltaVec = moveVec;
            }
            case MOVE_TO -> {
                Entity entity = params.getParamOrThrow(ParticleAnimParams.TARGET);
                Vec3 move = control.loc.subtract(MathHelper.getPosition(entity));
                control.loc = control.loc.add(move.scale(remaining / 100.));
            }
            case MOVE_AWAY -> {
                Entity entity = params.getParamOrThrow(ParticleAnimParams.SOURCE);
                Vec3 move = MathHelper.getPosition(entity).subtract(control.loc);
                control.deltaVec = move.scale(remaining / 100.);
            }
            case ROTATE -> {
                Entity entity = params.getParamOrThrow(ParticleAnimParams.SOURCE);
                int rot = params.getParamOrThrow(ParticleAnimParams.ROTATION);
                Vec3 outRot = MathHelper.calculateViewVector(0, rot);
                Vec3 dist = MathHelper.getPosition(entity).subtract(control.loc);
            }
        }
        double particleSpeed = new Vec3(particle.xd, particle.yd, particle.zd).length();
        control.deltaVec = MathHelper.maximiseLength(control.deltaVec, particleSpeed);
        control.applyToParticle(particle);
    }
}