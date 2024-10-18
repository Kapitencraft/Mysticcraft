package net.kapitencraft.mysticcraft.gui.screen;

import net.kapitencraft.mysticcraft.gui.IMenu;
import net.kapitencraft.mysticcraft.gui.screen.tooltip.HoverTooltip;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;

public abstract class HoverScreenUpdatable<T extends AbstractContainerMenu & IMenu> extends HoverTooltip {
    protected final T menu;
    public HoverScreenUpdatable(int xOffsetStart, int yOffsetStart, int xSize, int ySize, T menu) {
        super(xOffsetStart, yOffsetStart, xSize, ySize, new ArrayList<>());
        this.menu = menu;
    }

    public abstract void tick();

    public abstract boolean changed();
}
