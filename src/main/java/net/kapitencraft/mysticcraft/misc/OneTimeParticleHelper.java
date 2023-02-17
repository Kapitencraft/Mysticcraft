package net.kapitencraft.mysticcraft.misc;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class OneTimeParticleHelper {

    public static void createArrowHead(Level level, ParticleType<SimpleParticleType> type1, ParticleType<SimpleParticleType> type2, int maxParticles, int maxDistance, float rotation, Vec3 pos, Entity target) {
        ParticleGradientHolder[] gradientHolders = new ParticleGradient(maxDistance, maxParticles, type1, type2).generate();
        for (int y = 0; y < 2; y++) {
            for (int i = y; i <= 10; i++) {
                Vec3 targetLoc = MISCTools.calculateViewVector(target.getXRot(), rotation + (y == 0 ? 120 : -120)).scale(1 + (i * 0.05)).add(MISCTools.getPosition(target));
                MISCTools.sendParticles(level, false, targetLoc, 0, 0, 0, 0, gradientHolders[i]);
            }
        }
    }
}