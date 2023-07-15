package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SkeletonMaster extends Monster {
    public static final int ARROW_AMOUNT = 10;
    public static final double ARROW_SPAWN_LENGTH = 1;
    private final ArrowKeeper toShoot = new ArrowKeeper();
    private List<ControlledArrow> arrows;
    private int attackCooldown;
    private int reloadCooldown;
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
    public void tick() {
        super.tick();
        reloadTick();
        checkArrowKilled();
        if (this.attackCooldown == 0) {
            if (this.getTarget() != null) {
                this.shootArrow(this.toShoot.getList().get(0));
                this.attackCooldown = Mth.nextInt(MysticcraftMod.RANDOM_SOURCE, 5, 20);
            }
        } else {
            this.attackCooldown--;
        }
    }


    private void checkArrowKilled() {
        this.toShoot.removeIf(Entity::isRemoved);
    }

    private void reloadTick() {
        if (reloadCooldown <= 0) {
            RandomSource source = RandomSource.create();
            this.spawnArrows();
            this.reloadCooldown = Mth.nextInt(source, 15, 35);
        } else {
            reloadCooldown--;
        }
    }

    private void spawnArrows() {
        if (this.toShoot.getSize() >= 10) {
            return;
        }
        List<ControlledArrow> arrows = new ArrayList<>();
        for (int i = 0; i < SkeletonMaster.ARROW_AMOUNT; i++) {
            //TODO Fix Arrows not being placed on the right pos
            ControlledArrow arrow = new ControlledArrow(this.level, this, i + this.toShoot.getSize() * SkeletonMaster.ARROW_AMOUNT, this.toShoot.getSize() % 2 == 0);
            arrow.setBaseDamage(15);
            this.level.addFreshEntity(arrow);
            arrows.add(arrow);
        }
        this.toShoot.addList(arrows);
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

    private static class ArrowKeeper {
        private final List<List<ControlledArrow>> arrows = new ArrayList<>();

        public ArrowKeeper() {
        }

        public int getSize() {
            return arrows.size();
        }

        public void addList(List<ControlledArrow> list) {
            this.arrows.add(list);
        }

        public void removeList() {
            if (this.getSize() > 0) {
                this.arrows.remove(this.arrows.size() - 1);
            }
        }

        public List<ControlledArrow> getList() {
            if (this.getSize() > 0) {
                return this.arrows.get(this.arrows.size() - 1);
            }
            return List.of();
        }

        public void forEach(Consumer<ControlledArrow> consumer) {
            for (List<ControlledArrow> list : this.arrows) {
                list.forEach(consumer);
            }
        }

        public void removeIf(Predicate<ControlledArrow> predicate) {
            this.arrows.removeIf(List::isEmpty);
            for (List<ControlledArrow> list : this.arrows) {
                list.removeIf(predicate);
            }
        }
    }
}