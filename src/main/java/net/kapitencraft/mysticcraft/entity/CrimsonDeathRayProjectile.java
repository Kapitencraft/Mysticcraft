package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrimsonDeathRayProjectile extends AbstractArrow {
    private int timeTravelled = 0;
    public CrimsonDeathRayProjectile(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        this.setNoGravity(true);
        this.setNoPhysics(true);
    }

    private CrimsonDeathRayProjectile(Level level, LivingEntity owner, float yRot) {
        super(ModEntityTypes.CRIMSON_DEATH_RAY.get(), level);
        this.setOwner(owner);
        this.setYRot(yRot);
        new ParticleHelper("crimsonProjectile", this, ParticleHelper.Type.ARROW_HEAD, ParticleHelper.createArrowHeadProperties(10, 20, ParticleTypes.FLAME, ParticleTypes.ASH));
        this.setPos(MathUtils.getPosition(owner).add(0, 0.1, 0));
        this.setNoGravity(true);
        this.setNoPhysics(true);
        this.setDeltaMovement(this.getViewVector(1));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
    }

    @Override
    public boolean save(@NotNull CompoundTag tag) {
        tag.putInt("ticksAlive", this.timeTravelled);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.timeTravelled = tag.getInt("ticksAlive");
        super.load(tag);
    }

    @Override
    public void tick() {
        super.tick();
        List<LivingEntity> possibleTargetEntities = MiscUtils.sortLowestDistance(this, MathUtils.getLivingAround(this, 3));
        for (LivingEntity living : possibleTargetEntities) {
            if (!this.ownedBy(living) && !living.isDeadOrDying()) {
                Vec3 targetVec = MathUtils.getPosition(living).subtract(MathUtils.getPosition(this));
                this.setDeltaMovement(MathUtils.maximiseLength(targetVec, 0.5));
                break;
            }
        }
        if (this.timeTravelled++ > 80) {
            this.kill();
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public static CrimsonDeathRayProjectile createProjectile(Level level, LivingEntity owner, float yRot) {
        return new CrimsonDeathRayProjectile(level, owner, yRot);
    }
}
