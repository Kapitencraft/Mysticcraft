package net.kapitencraft.mysticcraft.entity.vampire;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class VampireSyphonAttackGoal<T extends Mob & IVampire> extends Goal {
    private final T owner;

    public VampireSyphonAttackGoal(T owner) {
        this.owner = owner;
    }

    @Override
    public boolean canUse() {
        return owner.getTarget() != null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    private void attack(LivingEntity living) {
        if (owner.isWithinMeleeAttackRange(living)) {
            owner.doHurtTarget(living);
        }
    }
}
