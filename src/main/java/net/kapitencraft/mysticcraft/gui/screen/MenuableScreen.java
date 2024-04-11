package net.kapitencraft.mysticcraft.gui.screen;

import net.kapitencraft.mysticcraft.gui.IMenuBuilder;
import net.kapitencraft.mysticcraft.gui.menu.Menu;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class MenuableScreen extends Screen {
    protected MenuableScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        GuiEventListener listener = null;

        for(GuiEventListener eventListener : List.copyOf(this.children())) {
            if (eventListener instanceof IMenuBuilder builder && pButton == 1) {
                listener = builder.createMenu((int) pMouseX, (int) pMouseY);
                ((Menu) listener).show();
            } else if (eventListener.mouseClicked(pMouseX, pMouseY, pButton)) {
                listener = eventListener;
            }
        }

        if (listener != this.getFocused() && this.getFocused() instanceof Menu menu) {
            menu.hide(this);
            return true;
        }
        if (listener != null) {
            this.setFocused(listener);
            if (pButton == 0) {
                this.setDragging(true);
            }

            return true;
        } else {
            return false;
        }
    }
}
