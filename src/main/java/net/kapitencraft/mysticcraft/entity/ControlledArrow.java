package net.kapitencraft.mysticcraft.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ControlledArrow extends Arrow {

    private static final EntityDataAccessor<Float> ROTATION_X = SynchedEntityData.defineId(ControlledArrow.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Y = SynchedEntityData.defineId(ControlledArrow.class, EntityDataSerializers.FLOAT);

    boolean hasBeenFired = false;
    public ControlledArrow(Level p_36861_, double p_36862_, double p_36863_, double p_36864_) {
        super(p_36861_, p_36862_, p_36863_, p_36864_);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (result.getEntity() != this.getOwner()) {
            super.onHitEntity(result);
            this.discard();
        }
    }

    @Override
    public void setDeltaMovement(@NotNull Vec3 vec3) {
        if (hasBeenFired) super.setDeltaMovement(vec3);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            this.entityData.set(ROTATION_X, this.getXRot());
            this.entityData.set(ROTATION_Y, this.getYRot());
        } else {
            this.setXRot(this.entityData.get(ROTATION_X));
            this.setYRot(this.entityData.get(ROTATION_Y));
        }
        if (this.getDeltaMovement() == Vec3.ZERO || this.isInFluidType()) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROTATION_X, 0f);
        this.entityData.define(ROTATION_Y, 0f);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        this.discard();
    }

    public void fire() {
        this.setCritArrow(true);
        this.hasBeenFired = true;
    }
}
