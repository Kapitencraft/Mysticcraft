package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class InstantTransmissionSpell implements Spell {

    @Override
    public void cast(SpellCastContext context) {
        MiscHelper.saveTeleport(context.getCaster(), context.getLevel() * 2);
        context.getCaster().setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public double manaCost() {
        return 50;
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
        return true;
    }
}
