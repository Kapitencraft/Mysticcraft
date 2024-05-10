package net.kapitencraft.mysticcraft.gui.screen.menu.scroll;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.UsefulTextures;
import net.kapitencraft.mysticcraft.gui.screen.menu.IMenuElement;
import net.kapitencraft.mysticcraft.gui.screen.menu.Menu;
import net.kapitencraft.mysticcraft.gui.screen.menu.scroll.elements.ScrollElement;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScrollableMenu extends Menu implements IMenuElement {
    private static final int SLIDER_WIDTH = 6;
    private final List<ScrollElement> elements = new ArrayList<>();
    private final int elementsPerPage;
    private final int stableWidth;
    private int startIndex;
    public ScrollableMenu(int x, int y, GuiEventListener parent, int elementsPerPage, int stableWidth) {
        super(x, y, parent);
        this.elementsPerPage = elementsPerPage;
        this.stableWidth = stableWidth;
    }

    @Override
    public void show() {

    }

    private int width() {
        return startIndex == -1 ? MathHelper.getLargest(this.elements
                .stream()
                .map(ScrollElement::getWidth)
                .toList()) : stableWidth;
    }

    @Override
    protected int height() {
        return this.elements.get(0).getHeight() * elementsPerPage;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (pMouseX - this.x < this.width() && pMouseY - this.y < this.height()) {
            this.startIndex += (pDelta / 10);
            this.startIndex = Math.max(0, startIndex);
            return true;
        }
        return false;
    }

    //TODO add slider

    public void addScrollable(ScrollElement element) {
        elements.add(element);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (elements.size() < 1) {
            ClientHelper.drawCenteredString(pPoseStack, this.x, this.y, this.width(), this.height(), Component.translatable("gui.nothing_to_show"), -1);
            return;
        }
        if (elements.size() > elementsPerPage) {
            int sliderX = this.width() + this.x - SLIDER_WIDTH;
            int relative = this.startIndex / this.elements.size();
            UsefulTextures.renderSlider(pPoseStack, sliderX, relative * this.height(), true, .5f);
        }
        int curY = this.y;
        for (int i = startIndex; i < Math.min(elements.size(), startIndex + elementsPerPage); i++) {
            ScrollElement element = elements.get(i);
            GuiComponent.fill(pPoseStack, this.x, curY, this.x + width(), curY + element.getHeight(), element.getBackgroundColor());
            element.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            curY += element.getHeight();
        }
    }
}
