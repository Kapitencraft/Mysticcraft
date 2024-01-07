package net.kapitencraft.mysticcraft.gui.screen;

import net.minecraft.network.chat.Component;

import java.util.List;

public class HoverTooltip {
    private final int xOffsetStart;
    private final int yOffsetStart;
    private final int xSize;
    private final int ySize;
    private final List<Component> text;

    public HoverTooltip(int xOffsetStart, int yOffsetStart, int xSize, int ySize, List<Component> text) {
        this.xOffsetStart = xOffsetStart;
        this.yOffsetStart = yOffsetStart;
        this.xSize = xSize;
        this.ySize = ySize;
        this.text = text;
    }

    public boolean matches(int xPos, int yPos, int xMousePos, int yMousePos) {
        return matchesX(xPos, xMousePos) && matchesY(yPos, yMousePos);
    }

    public List<Component> getText() {
        return text;
    }

    private boolean matchesX(int xPos, int xMousePos) {
        return xMousePos >= xPos + xOffsetStart && xMousePos <= xPos + xOffsetStart + xSize;
    }
    private boolean matchesY(int yPos, int yMousePos) {
        return yMousePos >= yPos + yOffsetStart && yMousePos <= yPos + yOffsetStart + ySize;
    }

}
