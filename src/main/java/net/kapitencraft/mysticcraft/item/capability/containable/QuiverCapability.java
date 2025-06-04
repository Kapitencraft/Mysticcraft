package net.kapitencraft.mysticcraft.item.capability.containable;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;

public class QuiverCapability extends ContainableCapability<ArrowItem> {

    @Override
    public boolean checkCanInsert(Item item) {
        return item.builtInRegistryHolder().is(ItemTags.ARROWS);
    }
}
