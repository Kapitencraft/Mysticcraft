package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.minecraft.world.item.Rarity;

public class VoidStaffItem extends SpellItem {
    public VoidStaffItem() {
        super(MiscHelper.rarity(Rarity.EPIC).attributes(createCatalystAttributes(5, -2.9f, 310, 20)));
    }
}
