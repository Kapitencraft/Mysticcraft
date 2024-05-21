package net.kapitencraft.mysticcraft.item.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IModCapability<T extends ModCapability<T, K>, K extends ICapability<T>> {

    void copy(T capability);

    Capability<? extends K> getCapability();

    LazyOptional<T> get();
}
