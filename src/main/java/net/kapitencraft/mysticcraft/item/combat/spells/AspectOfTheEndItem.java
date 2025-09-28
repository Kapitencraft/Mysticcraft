package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.world.item.Rarity;

public class AspectOfTheEndItem extends NormalSpellItem {
    public AspectOfTheEndItem(int intel) {
        super(new Properties().rarity(Rarity.UNCOMMON), intel, 0);
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.INSTANT_TRANSMISSION, 4);
    }
}
