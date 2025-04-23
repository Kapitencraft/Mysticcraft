package net.kapitencraft.mysticcraft.entity.skeleton_master;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster.ARROW_AMOUNT;
import static net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster.ARROW_SPAWN_LENGTH;

public class ControlledArrow extends Arrow {
    private final int volleyVal;
    private final boolean left;

    boolean hasBeenFired = false;

    public ControlledArrow(Level p_36866_, LivingEntity p_36867_, int volleyVal, boolean left) {
        super(p_36866_, p_36867_);
        this.volleyVal = volleyVal;
        this.left = left;
        this.updatePos();
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
        ParticleHelper.sendParticles(ParticleTypes.FLAME, true, this, 1, 0, 0, 0, 0);
        super.tick();
        if (((this.getDeltaMovement() == Vec3.ZERO || this.isInFluidType()) && this.hasBeenFired) || this.getOwner() == null || !this.getOwner().isAlive()) {
            this.discard();
        } else {
            updatePos();
        }
    }

    private void updatePos() {
        if (this.getOwner() != null) {
            int index = this.volleyVal % ARROW_AMOUNT;
            Vec3 deltaPos = MathHelper.calculateViewVector(0, this.getYRot() + 90);
            double offSetPerArrow = ARROW_SPAWN_LENGTH / ARROW_AMOUNT;
            double arrowOffset = offSetPerArrow * (index % 2 == 0 ? index : index - 1);
            double height = (this.volleyVal - index) * offSetPerArrow;
            if (this.left) {
                deltaPos.scale(2);
            } else {
                deltaPos.scale(-2);
            }
            deltaPos.scale(1 + (arrowOffset - ARROW_SPAWN_LENGTH / 2));
            this.setPos(new Vec3(deltaPos.x, 0.5 + height, deltaPos.z).add(this.getOwner().position()));
            this.setRot(this.getOwner().getXRot(), this.getOwner().getYRot());
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        this.discard();
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}