package net.kapitencraft.mysticcraft.gui.menu.drop_down;

import net.kapitencraft.mysticcraft.gui.menu.Menu;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.elements.Element;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.elements.ListElement;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class DropDownMenu extends Menu {
    private final ListElement root = new ListElement(this, Component.empty()) {
        @Override
        protected int width() {
            return 0;
        }
    };

    public DropDownMenu(int x, int y, GuiEventListener listener) {
        super(x, y, listener);
    }

    @Override
    public void show() {
        root.show(x, y);
    }

    @Override
    protected void render() {
        root.renderWithBackground();
    }

    @Override
    public void hide(Screen screen) {
        super.hide(screen);
        this.root.hide();
    }

    public void addElement(Function<DropDownMenu, Element> element) {
        this.root.addElement(element);
    }
}