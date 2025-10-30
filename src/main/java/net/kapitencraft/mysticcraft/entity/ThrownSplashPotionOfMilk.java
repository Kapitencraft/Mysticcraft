package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.EffectCures;

import java.util.List;

public class ThrownSplashPotionOfMilk extends ThrowableItemProjectile {

    public ThrownSplashPotionOfMilk(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSplashPotionOfMilk(LivingEntity shooter, Level level) {
        super(ModEntityTypes.SPLASH_POTION_OF_MILK.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.applySplash();
            this.level().levelEvent(2007, this.blockPosition(), 0xFFFFFF);
            this.discard();
        }
    }


    private void applySplash() {
        AABB aabb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                if (livingentity.isAffectedByPotions()) {
                    double d0 = this.distanceToSqr(livingentity);
                    if (d0 < 16.0) {
                        livingentity.removeEffectsCuredBy(EffectCures.MILK);
                    }
                }
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPLASH_POTION_OF_MILK.asItem();
    }
}
