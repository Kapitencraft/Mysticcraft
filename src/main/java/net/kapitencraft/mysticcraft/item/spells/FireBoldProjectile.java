package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class FireBoldProjectile extends AbstractArrow {
    private FireBoldProjectile(Level level, LivingEntity living) {
        super(ModEntityTypes.FIRE_BOLD.get(), living, level);
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    public FireBoldProjectile(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        Level level = this.level;
        level.addParticle((ParticleOptions) ModParticleTypes.FIRE_NORMAL.get(), this.getX(), this.getY(), this.getZ(), 0.1, 0.1, 0.1);
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (entity instanceof LivingEntity living) {
            living.hurt(new IndirectEntityDamageSource("ability", this, this.getOwner()), 1);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public static FireBoldProjectile createProjectile(Level level, LivingEntity owner) {
        return new FireBoldProjectile(level, owner);
    }
}