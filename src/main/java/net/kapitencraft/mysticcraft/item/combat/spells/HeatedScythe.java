package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.world.item.Rarity;

public class HeatedScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {

    public HeatedScythe() {
        super(new Properties().rarity(Rarity.UNCOMMON), 50, 0);
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_BOLT);
    }
}