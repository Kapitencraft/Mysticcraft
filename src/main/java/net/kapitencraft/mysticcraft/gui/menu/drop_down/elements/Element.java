package net.kapitencraft.mysticcraft.gui.menu.drop_down.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public abstract class Element {
    private static final int BACKGROUND_COLOR = 0xFF090909;
    public static final int OFFSET_PER_ELEMENT = 10;
    protected static final Font font = Minecraft.getInstance().font;
    protected final DropDownMenu menu;
    private final Component name;
    protected int x, y;
    protected boolean shown = false;

    protected Element(DropDownMenu menu, Component name) {
        this.menu = menu;
        this.name = name;
    }

    public abstract void render();

    public final void renderWithBackground() {
        PoseStack stack = new PoseStack();
        ClientHelper.fill(stack, x, y, x + width() + 2, y + OFFSET_PER_ELEMENT, BACKGROUND_COLOR);
        font.draw(stack, name, x + 1, y + 1, -1);
        render();
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



    protected int width() {
        return font.width(name);
    }
}
