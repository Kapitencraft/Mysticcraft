package net.kapitencraft.mysticcraft.spell;


import org.jetbrains.annotations.NotNull;

public class SpellSlot {
    private @NotNull Spells spell;

    public SpellSlot(@NotNull Spells spell) {
        this.spell = spell;
    }

    public @NotNull Spells getSpell() {
        return this.spell;
    }
}
