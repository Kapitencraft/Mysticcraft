package net.kapitencraft.mysticcraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
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
        this.goalSelector.addGoal(9, new WithermancerRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    private class WithermancerRangedAttackGoal extends Goal {
        private final WithermancerLordEntity lordEntity;

        public WithermancerRangedAttackGoal(WithermancerLordEntity lordEntity) {
            this.lordEntity = lordEntity;
        }

        @Override
        public boolean canUse() {
            LivingEntity living = this.lordEntity.getTarget();
            return living != null && living.isAlive() && this.lordEntity.canAttack(living);
        }

        public void start() {

        }

    }
}
