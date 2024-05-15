package net.kapitencraft.mysticcraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class UsefulTextures {
    public static final ResourceLocation CHECK_MARK = getGuiLocation("checkmark.png");
    public static final ResourceLocation CROSS = MysticcraftMod.res("textures/gui/red_cross.png");
    public static final ResourceLocation SLIDER = getGuiLocation("container/loom.png");
    public static final ResourceLocation ARROWS = getGuiLocation("server_selection.png");
    private static ResourceLocation getGuiLocation(String path) {
        return new ResourceLocation("textures/gui/" + path);
    }

    public static void renderCheckMark(PoseStack pPoseStack, int checkBoxX, int checkBoxY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CHECK_MARK);
        GuiComponent.blit(pPoseStack, checkBoxX, checkBoxY, 0, 0, 0, 8, 8, 8, 8);
    }

    public static void renderCross(PoseStack poseStack, int x, int y, int size) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CROSS);
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(size / 8f, size / 7f, 0);
        GuiComponent.blit(poseStack, 0, 0, 0, 0, 0, 8, 7, 8, 7);
        poseStack.popPose();
    }

    public static void renderSlider(PoseStack stack, int x, int y, boolean light, float scale) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SLIDER);
        stack.pushPose();
        stack.translate(x, y, 0);
        stack.scale(scale, scale, 0);
        GuiComponent.blit(stack, 0, 0, 232 + (light ? 0 : 12), 0, 12, 15, 256, 256);
        stack.popPose();
    }

    public static void renderUpButton(PoseStack pPoseStack, int pLeft, int pTop, boolean hovered, int size) {
        pPoseStack.pushPose();
        pPoseStack.translate(pLeft, pTop, 0);
        float scale = size / 16f;
        pPoseStack.scale(scale, scale, 0);
        RenderSystem.setShaderTexture(0, ARROWS);
        if (hovered) {
            GuiComponent.blit(pPoseStack, 0, 0, 96.0F, 32.0F, 32, 32, 256, 256);
        } else {
            GuiComponent.blit(pPoseStack, 0, 0, 96.0F, 0.0F, 32, 32, 256, 256);
        }
        pPoseStack.popPose();
    }

    public static void renderDownButton(PoseStack pPoseStack, int pLeft, int pTop, boolean hovered, int size) {
        pPoseStack.pushPose();
        pPoseStack.translate(pLeft, pTop - size, 0);
        pPoseStack.scale(size / 16f, size / 16f, 0);
        RenderSystem.setShaderTexture(0, ARROWS);
        if (hovered) {
            GuiComponent.blit(pPoseStack, 0, 0, 64.0F, 32.0F, 32, 32, 256, 256);
        } else {
            GuiComponent.blit(pPoseStack, 0, 0, 64.0F, 0.0F, 32, 32, 256, 256);
        }
        pPoseStack.popPose();
    }
}