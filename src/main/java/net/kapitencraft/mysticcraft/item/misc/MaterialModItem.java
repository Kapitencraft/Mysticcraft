package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MaterialModItem extends Item implements IModItem {
    private final TabGroup tabGroup;

    public MaterialModItem(Rarity rarity, boolean stackable, TabGroup group) {
        super(MiscHelper.rarity(rarity).stacksTo(stackable ? 64 : 1));
        this.tabGroup = group;
    }

    @Override
    public TabGroup getGroup() {
        return tabGroup;
    }
}
