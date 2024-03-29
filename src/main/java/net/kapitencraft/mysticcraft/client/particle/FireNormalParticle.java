package net.kapitencraft.mysticcraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FireNormalParticle extends TextureSheetParticle {


    protected FireNormalParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.setSize(0.2f, 0.2f);
        this.gravity = 0;
        this.hasPhysics = false;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.lifetime = xd > 1 || yd > 1 || zd > 1 ? 20 : 60;
        this.alpha = 0.5f;
        this.pickSprite(spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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