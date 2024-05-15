package net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ListElement extends Element {
    protected final List<Element> children = new ArrayList<>();
    private boolean showChildren = false;

    public ListElement(@Nullable ListElement listElement,  DropDownMenu menu, Component component) {
        super(listElement, menu, component);
    }

    public void addElement(Element element) {
        children.add(element);
    }

    public void showChildren() {
        int newY = y;
        int x = this.x + this.effectiveWidth();
        for (Element element : children) {
            element.show(x, newY);
            newY += OFFSET_PER_ELEMENT;
        }
        showChildren = true;
    }

    public void hideChildren() {
        this.children.forEach(Element::hide);
        showChildren = false;
    }

    protected boolean shouldShowChildren() {
        return showChildren;
    }

    @Override
    public void click(float relativeX, float relativeY) {
        if (this.shouldShowChildren()) this.children.forEach(element -> element.click(relativeX, relativeY));
    }

    public int childrenWidth() {
        return MathHelper.getLargest(this.children
                .stream()
                .map(Element::width)
                .toList()
        );
    }

    @Override
    public void startHovering(int x, int y) {
        super.startHovering(x, y);
        this.showChildren();
    }

    @Override
    public void endHovering() {
        super.endHovering();
        this.hideChildren();
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (shouldShowChildren()) this.children.forEach(element -> element.renderWithBackground(pPoseStack, pMouseX, pMouseY, pPartialTick));
    }

    @Override
    public void mouseMove(double absoluteX, double absoluteY) {
        if (this.shouldShowChildren()) this.children.forEach(element -> element.mouseMove(absoluteX, absoluteY));
        super.mouseMove(absoluteX, absoluteY);
    }

    protected boolean isChildrenFocused() {
        return this.shouldShowChildren() && this.children.stream().anyMatch(Element::isFocused);
    }

    @Override
    protected boolean isFocused() {
        return super.isFocused() || isChildrenFocused();
    }

    @Override
    public Element getFocused() {
        if (this.isChildrenFocused()) {
            for (Element element : this.children) {
                if (element.isFocused()) {
                    return element.getFocused();
                }
            }
        }
        return super.isFocused() ? this : null;
    }
}