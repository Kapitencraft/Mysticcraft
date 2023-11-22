package net.kapitencraft.mysticcraft.spell;


import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class SpellSlot {
    private @NotNull Spell spell;

    public SpellSlot(@NotNull Spell spell) {
        this.spell = spell;
    }

    public @NotNull Spell getSpell() {
        return this.spell;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Spell", this.spell.getRegistryName());
        return tag;
    }

    public static SpellSlot fromNbt(CompoundTag tag) {
        return new SpellSlot(Spells.getByName(tag.getString("Spell")));
    }
}
