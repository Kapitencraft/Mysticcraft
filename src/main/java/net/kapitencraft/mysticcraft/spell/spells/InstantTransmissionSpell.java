package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class InstantTransmissionSpell implements Spell {
    private static final Component[] description = new Component[]{Component.literal("teleports you 8 blocks ahead")};

    @Override
    public void cast(SpellCastContext context) {
        MiscHelper.saveTeleport(context.getCaster(), 8);
    }

    @Override
    public double manaCost() {
        return 50;
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
        return true;
    }
}
