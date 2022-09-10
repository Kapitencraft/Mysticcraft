package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.entity.Entity;

public class SlivyraItem extends SpellItem {

    public final int spellslotamount = 1;
    SpellSlot[] spellSlots = new SpellSlot[spellslotamount];

    public SlivyraItem(Properties p_43272_) {
        super(p_43272_);
        this.spellSlots[0] = new SpellSlot(Spells.CRYSTALWARP, null);
    }


    @Override
    public void execute(Entity user) {

    }
    @Override
    public SpellSlot[] getSpellSlots() {
        return this.spellSlots;
    }

    @Override
    public int getSpellslotamount() {
        return this.spellslotamount;
    }

    @Override
    public int getActivespell() {
        return 1;
    }
}
