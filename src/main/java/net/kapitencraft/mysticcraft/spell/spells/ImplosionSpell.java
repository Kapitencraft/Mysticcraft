package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;

public class ImplosionSpell implements Spell {
    @Override
    public void cast(SpellCastContext context) {
        ManaAOE.execute(context.getCaster(), this, 0.1f, 5, 5);
    }

    @Override
    public double manaCost() {
        return 300;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 100;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
