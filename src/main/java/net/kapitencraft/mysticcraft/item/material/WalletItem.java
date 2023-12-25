package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.tools.ContainableItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class WalletItem extends ContainableItem<Item> {
    public WalletItem() {
        super(MiscHelper.rarity(Rarity.UNCOMMON), 16);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public boolean canApply(Item item) {
        return item == Items.EMERALD;
    }
}
