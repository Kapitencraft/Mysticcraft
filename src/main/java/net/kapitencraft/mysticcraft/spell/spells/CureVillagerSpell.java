package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.Item;

public class CureVillagerSpell extends Spell {
    private static final SpellTarget<Entity> TARGET = SpellTarget.Type.ENTITY.create(ZombieVillager.class::isInstance);

    public CureVillagerSpell() {
        super(50, 60, Type.RELEASE, TARGET, null);
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        if (context.getParamOrNull(SpellCastContextParams.TARGET) instanceof ZombieVillager villager && villager.level() instanceof ServerLevel serverLevel) {
            villager.finishConversion(serverLevel);
        }
    }

    @Override
    public boolean canApply(Item item) {
        return false;
    }
}
