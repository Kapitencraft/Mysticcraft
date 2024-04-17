package net.kapitencraft.mysticcraft.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
    public static Screen postCommandScreen = null;

    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    public static void renderBeam(Vec3 start, LivingEntity living, int r, int g, int b, PoseStack stack, MultiBufferSource p_114833_) {
        float f1 = (float)living.level.getGameTime();
        float f2 = f1 * 0.5F % 1.0F;
        stack.pushPose();
        Vec3 stop = new Vec3(living.getX(), living.getY(), living.getZ()).add(0, living.getBbHeight() * 0.5, 0);
        Vec3 vec32 = stop.subtract(start);
        float f4 = (float)(vec32.length() + 1.0D);
        vec32 = vec32.normalize();
        float f5 = (float)Math.acos(vec32.y);
        float f6 = (float)Math.atan2(vec32.z, vec32.x);
        stack.mulPose(Axis.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
        stack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
        float f7 = f1 * 0.05F * -1.5F;
        float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
        float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
        float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
        float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
        float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
        float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
        float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
        float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
        float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
        float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
        float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f29 = -1.0F + f2;
        float f30 = f4 * 2.5F + f29;
        VertexConsumer vertexconsumer = p_114833_.getBuffer(BEAM_RENDER_TYPE);
        PoseStack.Pose pose = stack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, r, g, b, 0.4999F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, r, g, b, 0.4999F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, r, g, b, 0.0F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, r, g, b, 0.0F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, r, g, b, 0.4999F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, r, g, b, 0.4999F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, r, g, b, 0.0F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, r, g, b, 0.0F, f30);
        float f31 = 0.0F;
        if (living.tickCount % 2 == 0) {
            f31 = 0.5F;
        }
        vertex(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, r, g, b, 0.5F, f31 + 0.5F);
        vertex(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, r, g, b, 1.0F, f31 + 0.5F);
        vertex(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, r, g, b, 1.0F, f31);
        vertex(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, r, g, b, 0.5F, f31);
        stack.popPose();
    }

    private static void vertex(VertexConsumer p_253637_, Matrix4f p_253920_, Matrix3f p_253881_, float p_253994_, float p_254492_, float p_254474_, int p_254080_, int p_253655_, int p_254133_, float p_254233_, float p_253939_) {
        p_253637_.vertex(p_253920_, p_253994_, p_254492_, p_254474_).color(p_254080_, p_253655_, p_254133_, 255).uv(p_254233_, p_253939_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }


    public static <T> void addReqContent(Consumer<Component> consumer, T t, Player player) {
        List<Requirement<T>> reqs = CollectionHelper.mutableList((List<Requirement<T>>) Requirement.getReqs(t));
        reqs.removeIf(itemRequirement -> itemRequirement.matches(player));
        if (!reqs.isEmpty()) {
            MutableComponent reqList = Component.empty();
            reqs.stream().map(Requirement::display).forEach(reqList::append);
            consumer.accept(Component.translatable("item.requires", reqList).withStyle(ChatFormatting.RED));
        }
    }

    public static boolean hideGui() {
        return Minecraft.getInstance().options.hideGui;
    }

    @SuppressWarnings("all")
    public static void sendManaBoostParticles(Entity target, RandomSource random, Vec3 delta) {
        Level level = target.getLevel();
        if (!level.isClientSide()) return;
        ClientLevel clientLevel = (ClientLevel) level;
        Vec3 loc = MathHelper.getHandHoldingItemAngle(HumanoidArm.LEFT, target);
        addParticle(clientLevel, loc, random, delta);
        loc = MathHelper.getHandHoldingItemAngle(HumanoidArm.RIGHT, target);
        addParticle(clientLevel, loc, random, delta);
    }

    @SuppressWarnings("all")
    private static void addParticle(ClientLevel level, Vec3 loc, RandomSource random, Vec3 delta) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        SpriteSet spriteSet = engine.spriteSets.get(BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.FIREWORK.getType()));
        FireworkParticles.SparkParticle particle = new FireworkParticles.SparkParticle(level, loc.x, loc.y, loc.z, random.nextGaussian() * 0.05D, -delta.y * 0.5D, random.nextGaussian() * 0.05D, engine, spriteSet);
        particle.setColor(0, 0, 1);
        particle.setFadeColor(MathHelper.RGBtoInt(new Vector3f(0.5f, 0, 0.5f)));
        engine.add(particle);
    }

    public static void fill(PoseStack p_93173_, float xStart, float yStart, float xEnd, float yEnd, int color, int blitOffset) {
        innerFill(p_93173_.last().pose(), xStart, yStart, xEnd, yEnd, color, blitOffset);
    }

    private static void innerFill(Matrix4f p_254518_, float xStart, float yStart, float xEnd, float yEnd, int color, int blitOffset) {
        if (xStart < xEnd) {
            float i = xStart;
            xStart = xEnd;
            xEnd = i;
        }

        if (yStart < yEnd) {
            float i = yStart;
            yStart = yEnd;
            yEnd = i;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(p_254518_, xStart, yEnd, blitOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(p_254518_, xEnd, yEnd, blitOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(p_254518_, xEnd, yStart, blitOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(p_254518_, xStart, yStart, blitOffset).color(f, f1, f2, f3).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void blit(PoseStack stack, float xStart, float xEnd, float yStart, float yEnd, float blitOffset, float u0, float u1, float v0, float v1) {
        Matrix4f matrix4f = stack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, xStart, yEnd, blitOffset).uv(u0, v1).endVertex();
        bufferbuilder.vertex(matrix4f, xEnd, yEnd, blitOffset).uv(u1, v1).endVertex();
        bufferbuilder.vertex(matrix4f, xEnd, yStart, blitOffset).uv(u1, v0).endVertex();
        bufferbuilder.vertex(matrix4f, xStart, yStart, blitOffset).uv(u0, v0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

}