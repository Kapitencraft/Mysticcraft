package net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.elements;

import net.kapitencraft.mysticcraft.gui.IMenuBuilder;
import net.kapitencraft.mysticcraft.gui.widgets.Widget;

public abstract class ScrollElement extends Widget implements IMenuBuilder {
    protected int x;
    protected int y;

    protected boolean visible;

    public abstract int getWidth();

    public int getHeight() {
        return 10;
    }

    public int getBackgroundColor() {
        return 0x00000000;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
