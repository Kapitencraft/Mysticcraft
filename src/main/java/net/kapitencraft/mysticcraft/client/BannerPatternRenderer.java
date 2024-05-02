package net.kapitencraft.mysticcraft.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Holder;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class BannerPatternRenderer {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();
    private static final ModelPart FLAG = MINECRAFT.getEntityModels().bakeLayer(ModelLayers.BANNER).getChild("flag");


    public static void renderBanner(PoseStack poseStack, int x, int y, List<Pair<Holder<BannerPattern>, DyeColor>> patterns, int height) {
        MultiBufferSource.BufferSource source = MINECRAFT.renderBuffers().bufferSource();
        poseStack.translate(x, y, 0.0F);
        poseStack.scale(24.0F * (height / 40f), -24.0F * (height / 40f), 1.0F);
        poseStack.translate(0.5F, 0.5F, 0.5F);
        float f = 0.6666667F;
        poseStack.scale(f, -f, -f);
        FLAG.xRot = 0.0F;
        FLAG.y = -32.0F;
        BannerRenderer.renderPatterns(poseStack, source, 15728880, OverlayTexture.NO_OVERLAY, FLAG, ModelBakery.BANNER_BASE, true, patterns);
        poseStack.popPose();
        source.endBatch();
    }

    public static void renderBannerFromStack(PoseStack poseStack, int x, int y, ItemStack stack, int height) {
        renderBanner(poseStack, x, y, fromStack(stack), height);
    }

    public static List<Pair<Holder<BannerPattern>, DyeColor>> fromStack(ItemStack bannerStack) {
        BannerItem banner = (BannerItem) bannerStack.getItem();
        return BannerBlockEntity.createPatterns(banner.getColor(), BannerBlockEntity.getItemPatterns(bannerStack));
    }
}
