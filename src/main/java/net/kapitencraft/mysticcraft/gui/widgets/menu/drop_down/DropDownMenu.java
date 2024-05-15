package net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.widgets.menu.Menu;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements.Element;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements.ListElement;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class DropDownMenu extends Menu {
    private final ListElement root = new ListElement(null, this, Component.empty()) {
        @Override
        protected boolean shouldShowChildren() {
            return true;
        }
    };


    public DropDownMenu(int x, int y, GuiEventListener listener) {
        super(x, y, listener);
    }

    @Override
    public void show() {
        root.show(x, y);
        root.showChildren();
    }


    @Override
    public void hide(Screen screen) {
        super.hide(screen);
        this.root.hide();
    }

    public void addElement(Function<ListElement, Element> constructor) {
        this.root.addElement(constructor.apply(this.root));
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        root.renderWithBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        if (pMouseX < this.x || pMouseY < this.y) return;
        this.root.mouseMove(pMouseX, pMouseY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            return this.root.mouseClick(pMouseX, pMouseY);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}