package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class EmptySpell implements Spell {
    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
    }

    @Override
    public double manaCost() {
        return 0;
    }

    @Override
    public int castDuration() {
        return 0;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @NotNull SpellTarget getTarget() {
        return SpellTarget.SELF;
    }

    @Override
    public boolean canApply(Item item) {
        return false;
    }
}
