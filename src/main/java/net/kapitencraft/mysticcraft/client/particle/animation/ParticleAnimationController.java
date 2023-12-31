package net.kapitencraft.mysticcraft.client.particle.animation;

import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.particle.Particle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ParticleAnimationController {
    private final List<Particle> toAnimate;
    private boolean done = false;
    private final Map<Integer, ParticleAnimations> animationsForTime;
    private final ParticleAnimationParameters parameters;
    int startTime = -1;

    public ParticleAnimationController(List<Particle> toAnimate, Map<Integer, ParticleAnimations> animationsForTime, ParticleAnimationParameters parameters) {
        this.toAnimate = toAnimate;
        this.animationsForTime = animationsForTime;
        this.parameters = parameters;
        this.toAnimate.forEach(particle -> particle.lifetime = animationLength());
    }

    public List<Particle> getToAnimate() {
        return toAnimate;
    }

    public ParticleAnimationParameters getParameters() {
        return parameters;
    }

    public Map<Integer, ParticleAnimations> getAnimationsForTime() {
        return animationsForTime;
    }

    public boolean mergeable(ParticleAnimationController controller) {
        return controller.parameters == this.parameters && controller.animationsForTime.equals(this.animationsForTime);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParticleAnimationController controller && mergeable(controller) && controller.toAnimate.equals(this.toAnimate);
    }

    public void merge(ParticleAnimationController controller) {
        if (mergeable(controller)) {
            this.toAnimate.addAll(controller.toAnimate);
            controller.done = true;
        }
    }

    public void animate(int time) {
        ParticleAnimations animation = forTimeStamp(time, parameters);
        if (animation != null && animation.test(parameters)) CollectionHelper.sync(toAnimate.stream(), parameters, animation);
    }

    public boolean isDone() {
        return done;
    }

    public boolean done(int time) {
        return isDone() || time >= animationLength();
    }

    private ParticleAnimations forTimeStamp(int time, ParticleAnimationParameters context) {
        for (Map.Entry<Integer, ParticleAnimations> entry : animationsForTime.entrySet()) {
            if (entry.getKey() > time) {
                context.remainingAnimTime = entry.getKey() - time;
                return entry.getValue();
            }
            time -= entry.getKey();
        }
        return null;
    }

    private int animationLength() {
        return MathHelper.count(animationsForTime.keySet());
    }
}