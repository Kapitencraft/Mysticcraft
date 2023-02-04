package net.kapitencraft.mysticcraft.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class HeliumFlameParticle extends TextureSheetParticle {

    public static HeliumFlameParticleProvider provider(SpriteSet spriteSet) {
        return new HeliumFlameParticleProvider(spriteSet);
    }
    protected HeliumFlameParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, SpriteSet spriteSet) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
        this.gravity = -0.25f;
        this.friction = 0.5f;
        this.lifetime = 50;
        this.hasPhysics = false;
        this.setSprite(spriteSet.get(RandomSource.createNewThreadLocalInstance()));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class HeliumFlameParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet SPRITE_SET;

        public HeliumFlameParticleProvider(SpriteSet sprite_set) {
            SPRITE_SET = sprite_set;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel clientLevel, double x, double y, double z, double vx, double vy, double vz) {
            return new HeliumFlameParticle(clientLevel, x, y, z, SPRITE_SET);
        }
    }
}
