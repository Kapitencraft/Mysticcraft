package net.kapitencraft.mysticcraft.item.capability;

public interface ICapability<T> {
    void copy(T t);

    T asType();
}
