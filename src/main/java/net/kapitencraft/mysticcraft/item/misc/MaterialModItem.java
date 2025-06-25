package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MaterialModItem extends Item {

    public MaterialModItem(Rarity rarity, boolean stackable) {
        super(MiscHelper.rarity(rarity).stacksTo(stackable ? 64 : 1));
    }
}
