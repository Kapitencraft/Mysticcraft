package net.kapitencraft.mysticcraft.spell.capability;

import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.kapitencraft.mysticcraft.spell.Spell;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpells implements AbstractCapability<List<PlayerSpells.SpellEntry>> {
    private final List<SpellEntry> spells = new ArrayList<>();

    @Override
    public void copyFrom(List<SpellEntry> data) {
        spells.clear();
        spells.addAll(data);
    }

    @Override
    public List<SpellEntry> getData() {
        return spells;
    }

    public static class SpellEntry {
        private final Spell spell;
        private int level;

        private SpellEntry(Spell spell) {
            this.spell = spell;
        }
    }
}