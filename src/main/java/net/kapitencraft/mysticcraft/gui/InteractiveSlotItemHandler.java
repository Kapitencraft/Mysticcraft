package net.kapitencraft.mysticcraft.gui;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InteractiveSlotItemHandler extends SlotItemHandler {
    public InteractiveSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

}
