package net.kapitencraft.mysticcraft.gui.screen.menu;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;

public abstract class Menu implements IMenuElement {
    protected final int x, y;
    private final GuiEventListener parent;

    public Menu(int x, int y, GuiEventListener parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public abstract void show();

    public void hide(Screen screen) {
        screen.setFocused(parent);
    }

    protected int height() {
        return 0;
    }
}
