package net.kapitencraft.mysticcraft.client.particle;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.AttackSweepParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ShadowSweepParticle extends AttackSweepParticle {

    protected ShadowSweepParticle(ClientLevel p_105546_, double p_105547_, double p_105548_, double p_105549_, double p_105550_, SpriteSet p_105551_) {
        super(p_105546_, p_105547_, p_105548_, p_105549_, p_105550_, p_105551_);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet set) {
            this.spriteSet = set;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double dX, double dY, double dZ) {
            return new ShadowSweepParticle(level, x, y, z, dX, spriteSet);
        }
    }
}
