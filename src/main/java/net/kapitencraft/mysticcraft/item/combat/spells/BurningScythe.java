package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.world.item.Rarity;

public class BurningScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public BurningScythe() {
        super(new Properties().fireResistant().rarity(Rarity.EPIC), 250, 50);
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_BOLT_3);
    }
}
