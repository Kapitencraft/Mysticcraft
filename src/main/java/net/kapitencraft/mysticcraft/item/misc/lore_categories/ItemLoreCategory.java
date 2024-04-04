package net.kapitencraft.mysticcraft.item.misc.lore_categories;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class ItemLoreCategory implements Function<ItemStack, @Nullable Component> {

    private final int priority;

    public ItemLoreCategory(int priority) {
        assertPriorityInRange(priority);
        this.priority = priority;
    }

    private void assertPriorityInRange(int priority) {
        if (priority > 100)
            throw new IllegalStateException("priority > 100 found: " + this.getClass().getCanonicalName());
        else if (priority < 1)
            throw new IllegalStateException("priority < 1 found: " + this.getClass().getCanonicalName());
    }

    public int getPriority() {
        return priority;
    }
}
