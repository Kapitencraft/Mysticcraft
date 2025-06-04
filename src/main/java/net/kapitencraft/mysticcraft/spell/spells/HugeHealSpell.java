package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.world.item.Item;

public class HugeHealSpell implements Spell {

    @Override
    public void cast(SpellCastContext context) {
        context.getParamOrThrow(SpellCastContextParams.CASTER).heal(5f);
    }

    @Override
    public double manaCost() {
        return 70;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 140;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
