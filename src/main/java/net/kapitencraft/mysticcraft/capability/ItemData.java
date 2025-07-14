package net.kapitencraft.mysticcraft.capability;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public interface ItemData<T, K extends ItemData<T, K>> {

    T loadData(ItemStack stack, Consumer<K> ifNull);

    void getDisplay(ItemStack stack, List<Component> list);

    String getTagId();

    void saveData(ItemStack stack, T t);
}
