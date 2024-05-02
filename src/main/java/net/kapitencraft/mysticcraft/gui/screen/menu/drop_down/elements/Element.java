package net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.DropDownMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;

public abstract class Element implements Renderable {
    private static final int BACKGROUND_COLOR = 0xFF090909, FOCUS_COLOR = 0xFF7F7F7F;
    public static final int OFFSET_PER_ELEMENT = 10;
    protected static final Font font = Minecraft.getInstance().font;
    protected final @Nullable ListElement parent;
    protected final DropDownMenu menu;
    private final Component name;
    protected int x, y;
    protected boolean shown = false, focused = false;

    protected Element(@Nullable ListElement parent, DropDownMenu menu, Component name) {
        this.parent = parent;
        this.menu = menu;
        this.name = name;
    }

    public final void renderWithBackground(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        if (this.parent != null) {
            int maxX = x + effectiveWidth();
            int maxY = y + OFFSET_PER_ELEMENT;
            switch (ClientModConfig.getFocusType()) {
                case OUTLINE -> {
                    if (this.focused) {
                        GuiComponent.fill(stack, x, y, maxX, maxY, FOCUS_COLOR);
                    }
                    GuiComponent.fill(stack, focused ? x + 1 : x, focused ? y + 1 : y, focused ? maxX - 1 : maxX, focused ? maxY - 1 : maxY, BACKGROUND_COLOR);
                }
                case BACKGROUND -> GuiComponent.fill(stack, x, y, maxX, maxY, focused ? FOCUS_COLOR : BACKGROUND_COLOR);
            }
            font.draw(stack, name, x + 1, y + 1, -1);
        }
        render(stack, mouseX, mouseY, partialTick);
    }

    public void show(int x, int y) {
        if (shown) throw new IllegalStateException("tried showing Element that's already shown!");
        this.x = x;
        this.y = y;
        this.shown = true;
    }

    public void hide() {
        this.shown = false;
    }

    public abstract void click(float relativeX, float relativeY);

    public void startHovering(int x, int y) {
        this.focused = true;
    }

    public void endHovering() {
        this.focused = false;
    }

    protected int width() {
        return font.width(name);
    }

    protected int effectiveWidth() {
        return this.parent == null ? 0 : this.parent.childrenWidth() + 2;
    }

    public void mouseMove(double mouseX, double mouseY) {
        onHover(mouseX, mouseY, true, () -> {
            if (!isFocused()) startHovering((int) (mouseX - this.x), (int) (mouseY - this.y));
        }, this::endHovering);
    }

    private void onHover(double mouseX, double mouseY, boolean includeChildren, Runnable ifHovered, Runnable onNotHovered) {
        double relativeX = mouseX - this.x;
        double relativeY = mouseY - this.y;
        if (relativeX >= 0 && relativeY >= 0 && relativeX <= this.effectiveWidth() && relativeY <= OFFSET_PER_ELEMENT) {
            ifHovered.run();
        } else if (isFocused() && (!includeChildren || !(this instanceof ListElement listElement && listElement.isChildrenFocused()))) {
            onNotHovered.run();
        }
    }


    public boolean mouseClick(double mouseX, double mouseY) {
        Element element = this.getFocused();
        if (element == null) return false;
        float relativeX = (float) (mouseX - this.x);
        float relativeY = (float) (mouseY - this.y);
        element.click(relativeX, relativeY);
        return true;
    }

    protected Element getFocused() {
        return this.isFocused() ? this : null;
    }

    protected boolean isFocused() {
        return focused;
    }

    public enum FocusTypes implements StringRepresentable {
        OUTLINE("outline"),
        BACKGROUND("background");


        private final String serializedName;

        FocusTypes(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}
