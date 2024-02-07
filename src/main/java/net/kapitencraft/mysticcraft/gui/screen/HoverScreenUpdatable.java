package net.kapitencraft.mysticcraft.gui.screen;

import net.kapitencraft.mysticcraft.gui.ModMenu;

import java.util.ArrayList;

public abstract class HoverScreenUpdatable<T extends ModMenu<?>> extends HoverTooltip {
    protected final T menu;
    public HoverScreenUpdatable(int xOffsetStart, int yOffsetStart, int xSize, int ySize, T menu) {
        super(xOffsetStart, yOffsetStart, xSize, ySize, new ArrayList<>());
        this.menu = menu;
    }

    public abstract void tick();

    public abstract boolean changed();
}
