package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SoulboundCore extends Item implements IModItem {
    public SoulboundCore() {
        super(MiscHelper.rarity(Rarity.EPIC));
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
