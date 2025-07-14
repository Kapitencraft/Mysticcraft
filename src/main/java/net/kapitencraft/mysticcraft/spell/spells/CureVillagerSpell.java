package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class CureVillagerSpell implements Spell {
    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        if (context.getParamOrNull(SpellCastContextParams.TARGET) instanceof ZombieVillager villager && villager.level() instanceof ServerLevel serverLevel) {
            villager.finishConversion(serverLevel);
        };
    }

    @Override
    public double manaCost() {
        return 50;
    }

    @Override
    public int castDuration() {
        return 60;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @NotNull SpellTarget getTarget() {
        return SpellTarget.ENTITY;
    }

    @Override
    public boolean canApply(Item item) {
        return false;
    }
}
