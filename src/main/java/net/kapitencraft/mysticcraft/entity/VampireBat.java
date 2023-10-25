package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class VampireBat extends Monster {
    public VampireBat(EntityType<? extends Monster> p_27412_, Level p_27413_) {
        super(p_27412_, p_27413_);
    }

    public VampireBat(Level level) {
        this(ModEntityTypes.VAMPIRE_BAT.get(), level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, true));
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    public float getVoicePitch() {
        return super.getVoicePitch() * 0.95F;
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_27451_) {
        return SoundEvents.BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity p_27415_) {
    }

    protected void pushEntities() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15D);
    }

    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions p_27441_) {
        return p_27441_.height / 2.0F;
    }
}
