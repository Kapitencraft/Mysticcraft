package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkeletonMaster extends Monster {
    private List<ControlledArrow> toShoot;
    private int cooldown;
    private int amount;
    public SkeletonMaster(EntityType<? extends SkeletonMaster> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_20135_, @NotNull BlockState p_20136_) {
        this.playSound(getStepSound(), 0.15f, 1.0f);
    }

    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_33034_) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.ATTACK_DAMAGE, 20);
        builder.add(Attributes.MAX_HEALTH, 50);
        builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        return builder;
    }

    @Override
    public void die(@NotNull DamageSource source) {
        super.die(source);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getTarget() != null) {
            if (this.toShoot == null || toShoot.size() == 0) {
                reload();
                spawnArrows();
            } else {
                if (this.cooldown == 0) {
                    ControlledArrow arrow = this.toShoot.get(0);
                    this.shootArrow(arrow);
                    this.toShoot.remove(0);
                } else {
                    this.cooldown--;
                }
            }
        } else {
            if (this.toShoot != null) {
                this.toShoot.forEach(Entity::kill);
            }
        }
    }


    private void reload() {
        this.amount = Mth.nextInt(RandomSource.create(), 10, 50);
    }

    private void spawnArrows() {
        List<ControlledArrow> arrows = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Vec3 arrowPos = getPosForEntity(i).add(MathUtils.getPosition(this));
            ControlledArrow arrow = new ControlledArrow(this.level, arrowPos.x, arrowPos.y, arrowPos.z);
            arrow.setOwner(this);
            arrow.setYRot(this.getYRot());
            arrow.setXRot(this.getXRot());
            arrow.setBaseDamage(15);
            this.level.addFreshEntity(arrow);
            arrows.add(arrow);
        }
        this.toShoot = arrows;
    }

    private Vec3 getPosForEntity(int index) {
        RandomSource source = RandomSource.create();
        return MathUtils.calculateViewVector(0, this.getYRot()).scale((index % 2 == 0 ? 2 : -2) + Mth.nextDouble(source, -0.5, 0.5)).add(0, 0.5 + Mth.nextDouble(source, 0, 2), 0);
    }
    private void shootArrow(ControlledArrow arrow) {
        if (this.getTarget() != null) {
            Vec2 rot = MathUtils.createTargetRotationFromEyeHeight(arrow, this.getTarget());
            arrow.setXRot(rot.x);
            arrow.setYRot(rot.y);
            arrow.fire();
            arrow.setDeltaMovement(MathUtils.calculateViewVector(rot.x, rot.y));
            this.setArrowCount(this.getArrowCount() - 1);
        }
    }
}