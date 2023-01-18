package net.kapitencraft.mysticcraft.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.DamageIndicator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DamageIndicatorRenderer extends EntityRenderer<DamageIndicator> {

    public DamageIndicatorRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public boolean shouldRender(DamageIndicator p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(DamageIndicator p_114482_) {
        return null;
    }

    @Override
    public void render(DamageIndicator indicator, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packetLight) {
        String display = indicator.getColor() + MysticcraftMod.MAIN_FORMAT.format(indicator.getDamage());
        Font fontRenderer = this.getFont();
        poseStack.translate((float)-fontRenderer.width(display) / 2f + 0.5f, 0, 0);
        fontRenderer.draw(poseStack, display, 0f, 0f, packetLight);
        poseStack.clear();
    }
}
