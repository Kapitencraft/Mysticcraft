package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class SelectSpellCastScreen extends Screen {
    private static final int MIDDLE_SIZE = 5;
    private int amount = 5;

    public SelectSpellCastScreen() {
        super(Component.translatable("spell.select.title"));
    }


    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int middleX = this.width / 2;
        int middleY = this.height / 2;
        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(middleX, middleY, 0);
        //float angle = 360f / amount;
        //for (int i = 0; i < amount; i++) {
        //    pose.pushPose();
        //    pose.mulPose(Axis.ZP.rotationDegrees(-90 + angle * i));
        //    pGuiGraphics.hLine(MIDDLE_SIZE, 35, 0, 0xFFFFFFFF);
        //    pose.popPose();
        //}
        double mousePositionRelativeToCenter = Math.toDegrees(Math.atan2(pMouseY - middleX, pMouseX - middleY));
        pose.popPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        int selectedIndex = getSelectedIndex(pMouseX - middleX, pMouseY - middleX);

        for (int i = 0; i < amount; i++) {
            drawSlice(builder, middleX, middleY, 10, 45, 90, ((i - 0.5f) / amount + 0.25f) * 360, ((i + 0.5f) / amount + 0.25f) * 360, 63, 161, 191, 60);
        }
        Tesselator.getInstance().end();
        RenderSystem.disableBlend();
        //TODO copy+paste :upside_down: https://github.com/baileyholl/Ars-Nouveau/blob/main/src/main/java/com/hollingsworth/arsnouveau/client/gui/radial_menu/GuiRadialMenu.java#L28

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE || pKeyCode == ModKeyMappings.SELECT_SPELL_CAST.getKey().getValue()) {
            this.onClose();
            return true;
        }
        return false;
    }

    public void drawSlice(
            BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int r, int g, int b, int a) {
        startAngle = (float) Math.toRadians(startAngle);
        endAngle = (float) Math.toRadians(endAngle);

        float pos1InX = x + radiusIn * (float) Math.cos(startAngle);
        float pos1InY = y + radiusIn * (float) Math.sin(startAngle);
        float pos1OutX = x + radiusOut * (float) Math.cos(startAngle);
        float pos1OutY = y + radiusOut * (float) Math.sin(startAngle);
        float pos2OutX = x + radiusOut * (float) Math.cos(endAngle);
        float pos2OutY = y + radiusOut * (float) Math.sin(endAngle);
        float pos2InX = x + radiusIn * (float) Math.cos(endAngle);
        float pos2InY = y + radiusIn * (float) Math.sin(endAngle);

        buffer.vertex(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
        buffer.vertex(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
        buffer.vertex(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
        buffer.vertex(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
    }

    public int getSelectedIndex(int relativeMouseX, int relativeMouseY) {
        double mousePositionInDegreesInRelationToCenterOfScreen = Math.toDegrees(Math.atan2(relativeMouseY, relativeMouseX));
        double mouseDistanceToCenterOfScreen = Math.sqrt(Math.pow(relativeMouseX, 2) + Math.pow(relativeMouseY, 2));
        for (int i = 0; i < amount; i++) {
            float sliceBorderLeft = (((i - 0.5f) / (float) amount) + 0.25f) * 360;
            float sliceBorderRight = (((i + 0.5f) / (float) amount) + 0.25f) * 360;
            if (mousePositionInDegreesInRelationToCenterOfScreen >= sliceBorderLeft && mousePositionInDegreesInRelationToCenterOfScreen < sliceBorderRight && mouseDistanceToCenterOfScreen >= 45 && mouseDistanceToCenterOfScreen < 90) {
                return i;
            }
        }
        return -1;
    }
}
