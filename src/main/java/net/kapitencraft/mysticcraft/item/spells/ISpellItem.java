package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;

public interface ISpellItem {
    int spellslotamount = 1;
    SpellSlot[] spellslots = new SpellSlot[spellslotamount];
    int activespell = 1;

    public void execute();

}
