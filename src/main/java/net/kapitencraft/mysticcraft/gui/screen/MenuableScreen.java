package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.IMenuBuilder;
import net.kapitencraft.mysticcraft.gui.screen.menu.Menu;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuableScreen extends Screen {
    private Menu active;
    protected MenuableScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        MiscHelper.ifNonNull(this.active, menu -> menu.render(pPoseStack, pMouseX, pMouseY, pPartialTick));
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        MiscHelper.ifNonNull(this.active, menu -> menu.mouseMoved(pMouseX, pMouseY));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        GuiEventListener listener = null;

        if (this.getFocused() instanceof Menu && this.getFocused().mouseClicked(pMouseX, pMouseY, pButton)) {
            listener = this.getFocused();
        } else for(GuiEventListener eventListener : List.copyOf(this.children())) {
            if (eventListener instanceof IMenuBuilder builder && pButton == 1) {
                this.active = builder.createMenu((int) pMouseX, (int) pMouseY);
                this.active.show();
                listener = this.active;
            } else if (eventListener.mouseClicked(pMouseX, pMouseY, pButton)) {
                listener = eventListener;
            }
        }

        if (listener != this.active && this.active != null) {
            this.active.hide(this);
            this.active = null;
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
