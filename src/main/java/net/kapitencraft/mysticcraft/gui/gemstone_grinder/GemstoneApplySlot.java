package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GemstoneApplySlot extends Slot {
    public GemstoneApplySlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem() instanceof GemstoneItem;
    }

}
