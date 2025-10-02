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
public class ModFlameParticle extends RisingParticle {


    protected ModFlameParticle(ClientLevel p_107631_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Color color) {
        super(p_107631_, x, y, z, xSpeed, ySpeed, zSpeed);
        this.rCol = color.r();
        this.gCol = color.g();
        this.bCol = color.b();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public record FlameParticleProvider(SpriteSet spriteSet) implements ParticleProvider<FlameParticleOptions> {

        @Override
        public @NotNull Particle createParticle(FlameParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double pXSpeed, double pYSpeed, double pZSpeed) {
            ModFlameParticle particle = new ModFlameParticle(level, x, y, z, pXSpeed, pYSpeed, pZSpeed, options.getColor());
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
