package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public abstract class SpellItem extends Item {
    public final int spellslotamount = 1;
    public SpellSlot[] spellslots = new SpellSlot[spellslotamount];
    int activespell = 1;

    public SpellItem(Properties p_41383_) {
        super(p_41383_);
    }

    public void execute(Entity user) {

    }

    public SpellSlot[] getSpellSlots() { return this.spellslots;}
    public int getSpellslotamount() { return this.spellslotamount;}
    public int getActivespell() {return this.activespell;}
}
