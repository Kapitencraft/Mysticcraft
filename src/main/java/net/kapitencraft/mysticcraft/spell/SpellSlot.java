package net.kapitencraft.mysticcraft.spell;


import net.kapitencraft.mysticcraft.spell.spells.Spell;
import org.jetbrains.annotations.NotNull;

public class SpellSlot {
    private @NotNull Spell spell;

    public SpellSlot(@NotNull Spell spell) {
        this.spell = spell;
    }

    public @NotNull Spell getSpell() {
        return this.spell;
    }
}
