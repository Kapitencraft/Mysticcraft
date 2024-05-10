package net.kapitencraft.mysticcraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class UsefulTextures {
    public static final ResourceLocation CHECK_MARK = getGuiLocation("checkmark.png");
    public static final ResourceLocation SLIDER = getGuiLocation("container/loom.png");
    private static ResourceLocation getGuiLocation(String path) {
        return new ResourceLocation("textures/gui/" + path);
    }

    public static void renderCheckMark(PoseStack pPoseStack, int checkBoxX, int checkBoxY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CHECK_MARK);
        GuiComponent.blit(pPoseStack, checkBoxX, checkBoxY, 0, 0, 0, 8, 8, 8, 8);
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
}
