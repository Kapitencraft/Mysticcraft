package net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.gui.screen.menu.range.simple.NumberRange;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class NumberElement<T extends Number> extends Element {
    private final NumberRange<T> range;
    private EditBox box;
    private final int width;

    public NumberElement(ListElement parent, DropDownMenu menu, Component name, NumberRange<T> range, int width) {
        super(parent, menu, name);
        this.width = width;
        this.range = range;
    }

    @Override
    public void click(float relativeX, float relativeY) {

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

    }
}