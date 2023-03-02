package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.misc.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrimsonDeathRayProjectile extends AbstractArrow {
    private float distanceTravelled = 0;
    public CrimsonDeathRayProjectile(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    private CrimsonDeathRayProjectile(Level level, LivingEntity owner, float yRot) {
        super(ModEntityTypes.CRIMSON_DEATH_RAY.get(), level);
        this.setOwner(owner);
        this.setYRot(yRot);
        new ParticleHelper("crimsonProjectile", this, ParticleHelper.Type.ARROW_HEAD, ParticleHelper.createArrowHeadProperties(10, 20, ParticleTypes.FLAME, ParticleTypes.ASH));
        this.setPos(MISCTools.getPosition(owner).add(0, 0.1, 0));
    }

    @Override
    public void tick() {
        MysticcraftMod.sendInfo("ticking");
        List<LivingEntity> possibleTargetEntities = MISCTools.sortLowestDistance(this, this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5)));
        Vec2 targetRot = null;
        if (possibleTargetEntities != null) {
            for (LivingEntity living : possibleTargetEntities) {
                if (!this.ownedBy(living) && !living.isDeadOrDying()) {
                    targetRot = MISCTools.getTargetRotation(this, living);
                }
            }
        }
        if (targetRot != null) {
            if (targetRot.x > this.getXRot()) {
                this.setXRot(Math.min(this.getXRot() + 3, targetRot.x));
            } else if (targetRot.x < this.getXRot()) {
                this.setXRot(Math.max(this.getXRot() - 3, targetRot.x));
            }
            if (targetRot.y > this.getYRot()) {
                this.setYRot(Math.min(this.getYRot() + 3, targetRot.y));
            } else if (targetRot.y < this.getYRot()) {
                this.setYRot(Math.max(this.getYRot() - 3, targetRot.y));
            }
        }
        this.setPos(MISCTools.getPosition(this).add(this.getLookAngle()));
        this.distanceTravelled+=1;
        if (distanceTravelled >= 10) {
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
