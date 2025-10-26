package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;

public class EmptySpell extends Spell {
    public EmptySpell() {
        super(0, 0, Type.RELEASE, SpellTarget.Type.SELF.always(), null);
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
    }

    @Override
    public boolean canApply(Item item) {
        return false;
    }
}
