package net.kapitencraft.mysticcraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class WithermancerLordEntity extends Monster {
    public WithermancerLordEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 1000;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 18.0D).add(Attributes.MOVEMENT_SPEED, 0.43).add(Attributes.FOLLOW_RANGE, 50.0D).add(Attributes.MAX_HEALTH, 1000);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    private class WithermancerRangedAttackGoal extends Goal {
        private WithermancerLordEntity lordEntity;

        public WithermancerRangedAttackGoal(WithermancerLordEntity lordEntity) {
            this.lordEntity = lordEntity;
        }

        @Override
        public boolean canUse() {
            return false;
        }

        public void start() {

        }

    }
}
