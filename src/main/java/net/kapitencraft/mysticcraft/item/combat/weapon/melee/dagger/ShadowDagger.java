package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;

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
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.SHADOW_STEP));
    }
}
