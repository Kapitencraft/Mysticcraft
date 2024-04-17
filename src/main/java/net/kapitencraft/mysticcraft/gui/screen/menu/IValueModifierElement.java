package net.kapitencraft.mysticcraft.gui.screen.menu;

public interface IValueModifierElement<K extends IValueModifierElement<K , T>, T> {

    K setValue(T value);
}
