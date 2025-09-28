package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;

public class InfernalScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public InfernalScythe() {
        super(new Properties().rarity(ExtraRarities.LEGENDARY), 350, 69);
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_BOLT, 15);
    }
}
