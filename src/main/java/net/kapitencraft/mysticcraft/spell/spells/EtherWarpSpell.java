package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;

public class EtherWarpSpell extends Spell {

    public EtherWarpSpell() {
        super(50, 0, Type.RELEASE, SpellTarget.SELF, null);
    }

    @Override
    public void cast(SpellCastContext context) {
        MiscHelper.saveTeleport(context.getCaster(), 57);
    }
    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
