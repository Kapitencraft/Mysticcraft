package net.kapitencraft.mysticcraft.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
    public static Screen postCommandScreen = null;

    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    public static void renderBeam(Vec3 start, LivingEntity living, int r, int g, int b, float p_114831_, PoseStack stack, MultiBufferSource p_114833_) {
        float f1 = (float)living.level.getGameTime() + p_114831_;
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
}