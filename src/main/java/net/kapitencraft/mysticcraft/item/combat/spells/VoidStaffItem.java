package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.minecraft.world.item.Rarity;

public class VoidStaffItem extends SpellItem {
    public VoidStaffItem() {
        super(MiscHelper.rarity(Rarity.EPIC), 5, -2.9f, 310, 20);
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.empty();
    }
}
