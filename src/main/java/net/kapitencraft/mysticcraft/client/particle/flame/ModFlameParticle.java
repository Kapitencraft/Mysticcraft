package net.kapitencraft.mysticcraft.client.particle.flame;

import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ModFlameParticle extends TextureSheetParticle {
    protected ModFlameParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, SpriteSet spriteSet, Color color) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
        this.gravity = -0.85f;
        this.friction = 0.5f;
        this.lifetime = 25;
        this.hasPhysics = false;
        this.rCol = color.r();
        this.gCol = color.g();
        this.bCol = color.b();
        this.pickSprite(spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class FlameParticleProvider implements ParticleProvider<FlameParticleOptions> {
        private final SpriteSet spriteSet;

        public FlameParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(FlameParticleOptions options, ClientLevel level, double x, double y, double z, double p_107426_, double p_107427_, double p_107428_) {
            return new ModFlameParticle(level, x, y, z, spriteSet, options.getColor());
        }
    }
}
