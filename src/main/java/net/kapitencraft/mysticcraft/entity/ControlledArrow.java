package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.kapitencraft.mysticcraft.misc.utils.ParticleUtils;
import net.kapitencraft.mysticcraft.misc.utils.TextUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.kapitencraft.mysticcraft.entity.SkeletonMaster.ARROW_AMOUNT;
import static net.kapitencraft.mysticcraft.entity.SkeletonMaster.ARROW_SPAWN_LENGTH;

public class ControlledArrow extends Arrow {

    private static final EntityDataAccessor<Float> ROTATION_X = SynchedEntityData.defineId(ControlledArrow.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION_Y = SynchedEntityData.defineId(ControlledArrow.class, EntityDataSerializers.FLOAT);
    private final int volleyVal;

    boolean hasBeenFired = false;

    public ControlledArrow(Level p_36866_, LivingEntity p_36867_, int volleyVal) {
        super(p_36866_, p_36867_);
        this.volleyVal = volleyVal;
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (result.getEntity() != this.getOwner()) {
            super.onHitEntity(result);
            MysticcraftMod.sendInfo("discarded due to Entity Hit");
            this.discard();
        }
    }

    @Override
    public void setDeltaMovement(@NotNull Vec3 vec3) {
        if (hasBeenFired) super.setDeltaMovement(vec3);
    }

    @Override
    public void tick() {
        ParticleUtils.sendParticles(ParticleTypes.FLAME, true, this, 1, 0, 0, 0, 0);
        super.tick();
        if (((this.getDeltaMovement() == Vec3.ZERO || this.isInFluidType()) && this.hasBeenFired) || this.getOwner() == null || !this.getOwner().isAlive()) {
            MysticcraftMod.sendInfo("discarded due to tick");
            this.discard();
        } else {
            updatePos();
        }
    }

    private void updatePos() {
        if (this.getOwner() == null) {
            MysticcraftMod.sendInfo("updating Pos...");
            int index = this.volleyVal % ARROW_AMOUNT;
            Vec3 deltaPos = MathUtils.calculateViewVector(0, this.getYRot());
            double offSetPerArrow = ARROW_SPAWN_LENGTH / ARROW_AMOUNT;
            double arrowOffset = offSetPerArrow * (index % 2 == 0 ? index : index - 1);
            if (index % 2 == 0) {
                deltaPos.scale(2);
            } else {
                deltaPos.scale(-2);
            }
            deltaPos.scale(1 + (arrowOffset - ARROW_SPAWN_LENGTH / 2));
            MysticcraftMod.sendInfo(TextUtils.fromVec3(deltaPos));
            this.setPos(new Vec3(deltaPos.x, 0.5 + (this.volleyVal - index) * offSetPerArrow, deltaPos.z).add(MathUtils.getPosition(this.getOwner())));
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        MysticcraftMod.sendInfo("discarded due to Block Hit");
        this.discard();
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public void fire() {
        this.setCritArrow(true);
        this.hasBeenFired = true;
    }
}