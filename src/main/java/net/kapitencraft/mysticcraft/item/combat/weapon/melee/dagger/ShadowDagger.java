package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;

public class ShadowDagger extends DarkDagger implements ISpellItem {

    public ShadowDagger() {
        super(ExtraRarities.LEGENDARY, 3);
    }

    @Override
    public double getStrenght() {
        return 35;
    }

    @Override
    public double getCritDamage() {
        return 100;
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.SHADOW_STEP);
    }
}
