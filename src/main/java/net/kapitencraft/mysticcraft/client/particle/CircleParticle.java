package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class CircleParticle extends TextureSheetParticle {
    private final double expandSize;
    private final int startColor;
    private final double expandSpeed;
    public CircleParticle(ClientLevel level, double x, double y, double z, Color startColor, double expandSize, double expandSpeed) {
        super(level, x, y, z);
        this.startColor = startColor.pack();
        this.expandSize = expandSize;
        this.lifetime = 100;
        this.expandSpeed = expandSpeed;
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
        float lifePercentage = (float) age / lifetime;
        this.setAlpha(1f - lifePercentage);
        Color color = new Color(startColor);
        this.setColor(color.r, color.g, color.b);
        super.tick();
    }

    @Override
    public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float time) {
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
        makeCornerVertex(vertexConsumer, vertexPos[3], f7, f5, time);
        makeCornerVertex(vertexConsumer, vertexPos[2], f7, f4, time);
        makeCornerVertex(vertexConsumer, vertexPos[1], f6, f4, time);
        makeCornerVertex(vertexConsumer, vertexPos[0], f6, f5, time);
    }

    private void makeCornerVertex(VertexConsumer consumer, Vector3f vector3f, float u1, float v1, float time) {
        consumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(getLightColor(time)).endVertex();
    }

    @Override
    public float getQuadSize(float pScaleFactor) {
        return (float) (this.expandSize * (age / (age + expandSpeed * 10)));
    }

    public static class Provider implements ParticleProvider<CircleParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(CircleParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CircleParticle(pLevel, pX, pY, pZ, pType.getColor(), pType.getSize(), pType.getExpandSpeed()).withTexture(spriteSet);
        }
    }
}
