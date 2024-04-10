package net.kapitencraft.mysticcraft.gui.menu.drop_down.elements;

import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.gui.menu.range.simple.NumberRange;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class NumberElement<T extends Number> extends Element {
    private final NumberRange<T> range;
    private EditBox box;
    private final int width;

    public NumberElement(DropDownMenu menu, Component name, NumberRange<T> range, int width) {
        super(menu, name);
        this.width = width;
        this.range = range;
    }

    @Override
    public void render() {

    }

    @Override
    public void click(float relativeX, float relativeY) {

    }
}