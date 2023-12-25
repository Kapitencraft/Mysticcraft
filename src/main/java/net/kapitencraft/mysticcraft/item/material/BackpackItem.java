package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.tools.ContainableItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BackpackItem extends ContainableItem<Item> {
    public BackpackItem() {
        super(MiscHelper.rarity(Rarity.EPIC), 20);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    protected boolean canApply(Item item) {
        return true;
    }
}
