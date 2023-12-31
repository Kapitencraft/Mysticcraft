package net.kapitencraft.mysticcraft.item.material.containable;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BackpackItem extends GUIContainableItem<Item> {
    public BackpackItem() {
        super(MiscHelper.rarity(Rarity.EPIC), 64, 10, "item.backpack");
    }

    @Override
    public TabGroup getGroup() {
        return TabGroup.MATERIAL;
    }
}
