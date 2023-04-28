package net.kapitencraft.mysticcraft.particle.flame;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DarkBlueFlame extends ModFlameParticle {
    protected DarkBlueFlame(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, SpriteSet spriteSet) {
        super(p_108323_, p_108324_, p_108325_, p_108326_, spriteSet);
    }

    public static class RisingFlameParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet SPRITE_SET;

        public RisingFlameParticleProvider(SpriteSet sprite_set) {
            SPRITE_SET = sprite_set;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double vx, double vy, double vz) {
            return new DarkBlueFlame(clientLevel, x, y, z, SPRITE_SET);
        }
    }

}
