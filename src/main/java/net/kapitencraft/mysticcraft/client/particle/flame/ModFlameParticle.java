package net.kapitencraft.mysticcraft.client.particle.flame;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class ModFlameParticle extends TextureSheetParticle {
    protected ModFlameParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, SpriteSet spriteSet) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
        this.gravity = -0.85f;
        this.friction = 0.5f;
        this.lifetime = 25;
        this.hasPhysics = false;
        this.pickSprite(spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

}
