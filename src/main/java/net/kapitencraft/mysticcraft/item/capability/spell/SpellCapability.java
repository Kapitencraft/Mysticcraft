package net.kapitencraft.mysticcraft.item.capability.spell;

import com.google.common.collect.Lists;
import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;

import java.util.List;

public class SpellCapability implements AbstractCapability<List<SpellSlot>> {
    private SpellSlot[] slots = new SpellSlot[] {new SpellSlot()};

    @Override
    public void copyFrom(List<SpellSlot> spellSlots) {
        this.slots = spellSlots.toArray(SpellSlot[]::new);
    }

    @Override
    public List<SpellSlot> getData() {
        return Lists.newArrayList(this.slots);
    }

    public void setSlot(int index, SpellSlot slot) {
        this.slots[index] = slot;
    }

    public SpellSlot getSlot(int index) {
        return this.slots[index];
    }

    public void clearSlot(int index) {
        this.slots[index] = null;
    }

    public boolean hasSpell(Spell spell) {
        for (SpellSlot slot : this.slots) {
            if (slot.getSpell() == spell) return true;
        }
        return false;
    }

    public int getFirstEmpty() {
        for (int i = 0; i < this.slots.length; i++) {
            if (this.slots[i] == null) return i;
        }
        return -1;
    }
}
