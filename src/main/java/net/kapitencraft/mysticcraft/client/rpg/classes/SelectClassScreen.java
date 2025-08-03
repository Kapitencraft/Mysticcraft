package net.kapitencraft.mysticcraft.client.rpg.classes;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SelectClassScreen extends Screen {
    protected SelectClassScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return super.isPauseScreen();
    }
}
