package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class CircleParticle extends TextureSheetParticle {
    private final double riseSize;
    private final int startColor;
    private final double riseSpeed;
    public CircleParticle(ClientLevel level, double x, double y, double z, Vector3f startColor, double riseSize, double riseSpeed) {
        super(level, x, y, z);
        this.startColor = MathHelper.RGBtoInt(startColor);
        this.riseSize = riseSize;
        this.lifetime = 100;
        this.riseSpeed = riseSpeed;
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
        float lifePercentage = MathHelper.makePercentage(age, lifetime);
        this.setAlpha(1f - lifePercentage);
        Vector3i color = MathHelper.intToRGB(startColor);
        float r = MathHelper.makePercentage(color.x, 255);
        float g = MathHelper.makePercentage(color.y, 255);
        float b = MathHelper.makePercentage(color.z, 255);
        this.setColor(r, g, b);
        super.tick();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float time) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(time, this.xo, this.x) - vec3.x);
        float f1 = (float)(Mth.lerp(time, this.yo, this.y) - vec3.y);
        float f2 = (float)(Mth.lerp(time, this.zo, this.z) - vec3.z);
        float x = 1;
        float y = 1;
        float z = 0;
        Vector3f[] vertexPos = new Vector3f[]{new Vector3f(-x, -y, -z), new Vector3f(-x, y, z), new Vector3f(x, y, -z), new Vector3f(x, -y, z)};
        float f3 = this.getQuadSize(time);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = vertexPos[i];
            vector3f.rotateX((float) Math.toRadians(90));
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
        return (float) (this.riseSize * (age / (age + riseSpeed * 10)));
    }

    public static class Provider implements ParticleProvider<CircleParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Nullable
        @Override
        public Particle createParticle(@NotNull CircleParticleOptions op, @NotNull ClientLevel level, double x, double y, double z, double a, double b, double c) {
            return new CircleParticle(level, x, y, z, op.getColor(), op.getSize(), op.getSize()).withTexture(spriteSet);
        }
    }
}
