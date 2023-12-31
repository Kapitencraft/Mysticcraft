package net.kapitencraft.mysticcraft.item.material.containable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ContainableItemHandler<T extends Item> extends ItemStackHandler {
    private final GUIContainableItem<T> item;

    public ContainableItemHandler(GUIContainableItem<T> item) {
        super(item.getStackSize());
        this.item = item;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.item.getStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }

    public GUIContainableItem<T> getItem() {
        return item;
    }
}
