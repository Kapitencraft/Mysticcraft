package net.kapitencraft.mysticcraft.client.particle.animation;

import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    private final Controller controller = Controller.create();



    public static class Controller {
        private final List<Frame> delayedChildren = new ArrayList<>();

        public static Controller create() {
            return new Controller();
        }

        public Controller addChildren(Frame children) {
            delayedChildren.add(children);
            return this;
        }

        public void tick(ServerLevel level) {
            this.delayedChildren.removeIf(frame -> {
                frame.tick(level);
                return frame.shouldRemove();
            });
        }
    }

    public static class Frame {
        private final ParticleOptions options;
        private final boolean force;
        private final Vec3 pos;
        private final int amount;
        private final Vec3 delta;
        private final double speed;
        private final int repeatTimes;
        private int repeatedTimes = 0;
        private final int cooldown;
        private int curTick;

        public Frame(ParticleOptions options, boolean force, Vec3 pos, int amount, Vec3 delta, double speed, int repeatTimes, int cooldown) {
            this.options = options;
            this.force = force;
            this.pos = pos;
            this.amount = amount;
            this.delta = delta;
            this.speed = speed;
            this.repeatTimes = repeatTimes;
            this.cooldown = cooldown;
            this.curTick = cooldown;
        }

        public void tick(ServerLevel serverLevel) {
            if (curTick > 0) {
                curTick--;
                return;
            }
            if (repeatedTimes < repeatTimes) {
                ParticleHelper.sendParticles(serverLevel, options, force, pos, amount, delta.x, delta.y, delta.z, speed);
                repeatedTimes++;
                curTick = cooldown;
            }
        }

        public boolean shouldRemove() {
            return repeatedTimes >= repeatTimes;
        }
    }
}
