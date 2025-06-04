package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;

public class EmptySpell implements Spell {
    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
    }

    @Override
    public double manaCost() {
        return 0;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }

    @Override
    public boolean canApply(Item item) {
        return false;
    }
}
