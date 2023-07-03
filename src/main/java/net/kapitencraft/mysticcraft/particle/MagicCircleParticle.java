package net.kapitencraft.mysticcraft.particle;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

public class MagicCircleParticle extends TextureSheetParticle {
    public static final Vector3i RED = new Vector3i(255, 0, 0);
    public static final Vector3i ORANGE = new Vector3i(255, 154, 0);
    public static final Vector3i GREEN = new Vector3i(25, 255, 0);

    private final @Nullable LivingEntity living;

    protected MagicCircleParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_, SpriteSet spriteSet, @Nullable LivingEntity living) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.setSprite(spriteSet.get(1, 1));
        this.living = living;
        this.age = 10;
    }

    @Override
    public void tick() {
        super.tick();
        if (living != null) {
            double mana = living.getAttributeValue(ModAttributes.MANA.get());
            double manaPercentage = mana / living.getAttributeValue(ModAttributes.MAX_MANA.get());
            if (manaPercentage <= 0.1) {
                setColor(RED);
            } else if (manaPercentage <= 0.3) {
                setColor(ORANGE);
            } else {
                setColor(GREEN);
            }
            if (manaPercentage == 0) {
                this.remove();
            } else {
                this.lifetime++;
            }
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    private void setColor(Vector3i color) {
        this.rCol = color.x;
        this.gCol = color.y;
        this.bCol = color.z;
    }

    public static class MagicCircleParticleProvider implements ParticleProvider<MagicCircleParticleType> {
        private final SpriteSet SPRITE_SET;


        public MagicCircleParticleProvider(SpriteSet spriteSet) {
            this.SPRITE_SET = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(MagicCircleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MagicCircleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, SPRITE_SET, type.getLiving());
        }
    }
}
