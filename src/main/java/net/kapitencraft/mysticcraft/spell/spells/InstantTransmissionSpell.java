package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class InstantTransmissionSpell extends Spell {

    public InstantTransmissionSpell() {
        super(50, 0, Type.RELEASE, SpellTarget.SELF, null);
    }

    @Override
    public void cast(SpellCastContext context) {
        MiscHelper.saveTeleport(context.getCaster(), context.getLevel() * 2);
        context.getCaster().setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
