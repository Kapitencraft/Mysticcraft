package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.world.item.Rarity;

public class TheStaffOfDestruction extends NormalSpellItem {
    public TheStaffOfDestruction() {
        super(new Properties().rarity(Rarity.RARE), 50, 20);

    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.EXPLOSIVE_SIGHT);
    }
}