package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.util.content.mana.ManaAOE;
import net.minecraft.world.item.Item;

public class ImplosionSpell extends Spell {
    public ImplosionSpell() {
        super(300, 20, Type.RELEASE, SpellTarget.SELF, ModCooldowns.IMPLOSION);
    }

    @Override
    public void cast(SpellCastContext context) {
        ManaAOE.execute(context.getCaster(), this.getHolder(), context.getLevel(), 5);
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
