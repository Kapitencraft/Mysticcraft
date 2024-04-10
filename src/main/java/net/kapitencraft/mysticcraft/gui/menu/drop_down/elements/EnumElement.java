package net.kapitencraft.mysticcraft.gui.menu.drop_down.elements;

import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.function.Function;

public class EnumElement<T extends Enum<T>> extends ListElement {
    private T selected;
    private final Function<T, Component> nameMapper;

    public EnumElement(DropDownMenu menu, Component component, T[] elements, Function<T, Component> nameMapper) {
        super(menu, component);
        this.nameMapper = nameMapper;
        Arrays.stream(elements).map(ListItem::new).forEach(this::addElement);
    }

    private void addElement(Element element) {
        children.add(element);
    }

    private class ListItem extends BooleanElement {
        private final T id;

        protected ListItem(T id) {
            super(EnumElement.this.menu, EnumElement.this.nameMapper.apply(id));
            this.id = id;
        }

        @Override
        public boolean selected() {
            return EnumElement.this.selected == this.id;
        }

        @Override
        public void setSelected(boolean selected) {
            if (selected) {
                EnumElement.this.selected = this.id;
            }
        }
    }
}