package net.kapitencraft.mysticcraft.item.combat.spells;

import net.minecraft.world.item.Rarity;

public class VoidStaffItem extends SpellItem {
    public VoidStaffItem(Properties properties) {
        super(properties.rarity(Rarity.EPIC).attributes(createCatalystAttributes(5, -2.9f, 310, 20)));
    }
}
