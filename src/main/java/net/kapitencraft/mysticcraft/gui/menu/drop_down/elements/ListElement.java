package net.kapitencraft.mysticcraft.gui.menu.drop_down.elements;

import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListElement extends Element {
    protected final List<Element> children = new ArrayList<>();

    public ListElement(DropDownMenu menu, Component component) {
        super(menu, component);
    }

    public void addElement(Function<DropDownMenu, Element> elementConstructor) {
        children.add(elementConstructor.apply(this.menu));
    }

    @Override
    public void show(int x, int y) {
        super.show(x, y);
        int newY = y;
        for (Element element : children) {
            element.show(x, newY);
            newY += OFFSET_PER_ELEMENT;
        }
    }

    @Override
    public void click(float relativeX, float relativeY) {
        this.children.forEach(element -> element.click(relativeX, relativeY));
    }

    @Override
    public void render() {
        if (shown) this.children.forEach(Element::renderWithBackground);
    }

    @Override
    protected int width() {
        return MathHelper.getLargest(this.children
                .stream()
                .map(Element::width)
                .toList()
        );
    }
}