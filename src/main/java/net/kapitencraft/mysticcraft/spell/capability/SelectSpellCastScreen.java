package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Quaternionf;

public class SelectSpellCastScreen extends Screen {
    private static final int MIDDLE_SIZE = 5;
    private int amount = 5;

    protected SelectSpellCastScreen() {
        super(Component.translatable("spell.select.title"));
    }


    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int middleX = this.width / 2;
        int middleY = this.height / 2;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(middleX, middleY, 0);
        float angle = 360f / amount;
        for (int i = 0; i < amount; i++) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle * i));
            pGuiGraphics.hLine(MIDDLE_SIZE, 35, 0, 0xFFFFFFFF);
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
