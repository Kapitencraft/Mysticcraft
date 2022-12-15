package net.kapitencraft.mysticcraft.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FireNormalParticle extends TextureSheetParticle {
    public static FireNormalParticleProvider provider(SpriteSet spriteSet) {
        return new FireNormalParticleProvider(spriteSet);
    }
    private final SpriteSet spriteSet;


    protected FireNormalParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.gravity = 0;
        this.hasPhysics = false;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.lifetime = xd > 1 || yd > 1 || zd > 1 ? 20 : 60;
        this.setSprite(this.spriteSet.get(RandomSource.createNewThreadLocalInstance()));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class FireNormalParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet SPRITE_SET;

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireNormalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.SPRITE_SET);
        }

        public FireNormalParticleProvider(SpriteSet spriteSet) {
            this.SPRITE_SET = spriteSet;
        }
    }
}