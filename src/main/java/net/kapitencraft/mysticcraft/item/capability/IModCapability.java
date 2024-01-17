package net.kapitencraft.mysticcraft.item.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IModCapability<T extends ModCapability<T, K>, K> {

    void copy(T capability);

    Capability<K> getCapability();

    LazyOptional<T> get();
}
