package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;

public class MakeRainSpell extends Spell {
    public MakeRainSpell() {
        super(1000, 100, Type.RELEASE, SpellTarget.SELF, null);
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        if (context.getWorld() instanceof ServerLevel sL)
            sL.setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(sL.getRandom()), true, false);
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
