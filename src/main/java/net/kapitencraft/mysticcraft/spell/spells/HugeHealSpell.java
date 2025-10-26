package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.world.item.Item;

public class HugeHealSpell extends Spell {

    public HugeHealSpell() {
        super(70, 50, Type.RELEASE, SpellTarget.SELF, ModCooldowns.HUGH_HEAL);
    }

    @Override
    public void cast(SpellCastContext context) {
        context.getParamOrThrow(SpellCastContextParams.CASTER).heal(context.getLevel() * 2.5f);
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
