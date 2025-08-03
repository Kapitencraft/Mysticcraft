package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class MakeRainSpell implements Spell {
    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        if (context.getLevel() instanceof ServerLevel sL)
            sL.setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(sL.getRandom()), true, false);
    }

    @Override
    public double manaCost() {
        return 1000;
    }

    @Override
    public int castDuration() {
        return 10;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @NotNull SpellTarget<?> getTarget() {
        return SpellTarget.SELF;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
