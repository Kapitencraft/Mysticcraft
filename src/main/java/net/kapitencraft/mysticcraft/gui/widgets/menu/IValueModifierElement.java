package net.kapitencraft.mysticcraft.gui.widgets.menu;

public interface IValueModifierElement<K extends IValueModifierElement<K , T>, T> {

    void setValue(T value);
}
