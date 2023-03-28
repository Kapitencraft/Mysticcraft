package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.kapitencraft.mysticcraft.misc.utils.TagUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimsonDeathRayProjectile extends AbstractArrow {
    private int timeTravelled = 0;
    private List<UUID> alreadyHit = new ArrayList<>();
    public CrimsonDeathRayProjectile(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
        this.setNoGravity(true);
    }

    private CrimsonDeathRayProjectile(Level level, LivingEntity owner, float yRot) {
        super(ModEntityTypes.CRIMSON_DEATH_RAY.get(), level);
        this.setOwner(owner);
        this.setYRot(yRot);
        new ParticleHelper("crimsonProjectile", this, ParticleHelper.Type.ARROW_HEAD, ParticleHelper.createArrowHeadProperties(10, 20, ParticleTypes.FLAME, ParticleTypes.ASH));
        this.setPos(MiscUtils.getPosition(owner).add(0, 0.1, 0));
        this.setNoGravity(true);
        this.setDeltaMovement(this.getViewVector(1));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        this.alreadyHit.add(hitResult.getEntity().getUUID());
    }

    @Override
    public boolean save(@NotNull CompoundTag tag) {
        tag.put("alreadyHit", TagUtils.putUuidList(this.alreadyHit));
        tag.putInt("ticksAlive", this.timeTravelled);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.alreadyHit = TagUtils.toList(TagUtils.getUuidArray(tag.getCompound("alreadyHit")));
        this.timeTravelled = tag.getInt("ticksAlive");
        super.load(tag);
    }

    @Override
    public void tick() {
        super.tick();
        List<LivingEntity> possibleTargetEntities = MiscUtils.sortLowestDistance(this, this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3)));
        if (possibleTargetEntities != null) {
            for (LivingEntity living : possibleTargetEntities) {
                if (!this.ownedBy(living) && !living.isDeadOrDying() && !this.alreadyHit.contains(living.getUUID())) {
                    Vec3 targetVec = MiscUtils.getPosition(living).subtract(MiscUtils.getPosition(this));
                    this.setDeltaMovement(MathUtils.maximiseLength(targetVec, 0.5));
                    this.alreadyHit.add(living.getUUID());
                    break;
                }
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
