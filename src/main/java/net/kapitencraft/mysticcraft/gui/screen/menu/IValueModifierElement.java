package net.kapitencraft.mysticcraft.gui.screen.menu;

public interface IValueModifierElement<K extends IValueModifierElement<K , T>, T> {

    void setValue(T value);
}
