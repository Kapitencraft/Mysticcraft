package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
        float angle = 360f / amount;
        for (int i = 0; i < amount; i++) {
            pose.pushPose();
            pose.mulPose(Axis.ZP.rotationDegrees(-90 + angle * i));
            pGuiGraphics.hLine(MIDDLE_SIZE, 35, 0, 0xFFFFFFFF);
            pose.popPose();
        }
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
}
