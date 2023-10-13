package net.kapitencraft.mysticcraft.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class CircleParticle extends TextureSheetParticle {
    private final double riseSpeed;
    private final double riseSize;
    private final int startColor;
    protected CircleParticle(ClientLevel level, double x, double y, double z, double startColor, double riseSpeed, double riseSize) {
        super(level, x, y, z);
        this.startColor = (int) startColor;
        this.riseSpeed = riseSpeed;
        this.riseSize = riseSize;
        this.lifetime = 100;
    }

    private CircleParticle withTexture(SpriteSet spriteSet) {
        this.setSprite(spriteSet.get(1, 1));
        return this;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        float lifePercentage = MathHelper.makePercentage(age, lifetime);
        this.setAlpha(1f - lifePercentage);
        Vector3i color = MathHelper.intToRGB(startColor);
        float r = MathHelper.makePercentage(color.x, 255);
        float g = MathHelper.makePercentage(color.y, 255);
        float b = MathHelper.makePercentage(color.z, 255);
        this.setColor(r * (1 - lifePercentage), g * (1 - lifePercentage), b * (1 -lifePercentage));
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float time) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(time, this.xo, this.x) - vec3.x);
        float f1 = (float)(Mth.lerp(time, this.yo, this.y) - vec3.y);
        float f2 = (float)(Mth.lerp(time, this.zo, this.z) - vec3.z);
        float x = 1;
        float y = 0;
        float z = 1;
        Vector3f[] vertexPos = new Vector3f[]{new Vector3f(-x, -y, -z), new Vector3f(-x, y, z), new Vector3f(x, y, -z), new Vector3f(x, -y, z)};
        float f3 = this.getQuadSize(time);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = vertexPos[i];
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        float f6 = this.getU0();
        float f7 = this.getU1();
        float f4 = this.getV0();
        float f5 = this.getV1();
        makeCornerVertex(vertexConsumer, vertexPos[0], f7, f5, time);
        makeCornerVertex(vertexConsumer, vertexPos[1], f7, f4, time);
        makeCornerVertex(vertexConsumer, vertexPos[2], f6, f4, time);
        makeCornerVertex(vertexConsumer, vertexPos[3], f6, f5, time);
    }

    private void makeCornerVertex(VertexConsumer consumer, Vector3f vector3f, float u1, float v1, float time) {
        consumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(getLightColor(time)).endVertex();
    }

    @Override
    public float getQuadSize(float p_107681_) {
        return 0.5f * (float) (this.riseSize * age * 0.25);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType p_107421_, @NotNull ClientLevel level, double x, double y, double z, double a, double b, double c) {
            return new CircleParticle(level, x, y, z, a, b, c).withTexture(spriteSet);
        }
    }
}
