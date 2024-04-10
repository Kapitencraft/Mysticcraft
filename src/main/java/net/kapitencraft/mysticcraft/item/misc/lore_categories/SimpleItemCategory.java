package net.kapitencraft.mysticcraft.item.misc.lore_categories;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SimpleItemCategory<T> extends ItemLoreCategory {
    private final Class<T> tClass;
    private final Component component;

    public SimpleItemCategory(int priority, Class<T> tClass, Component component) {
        super(priority);
        this.tClass = tClass;
        this.component = component;
    }

    @Override
    public @Nullable Component apply(ItemStack stack) {
        return tClass.isInstance(stack.getItem()) ? component : null;
    }
}
