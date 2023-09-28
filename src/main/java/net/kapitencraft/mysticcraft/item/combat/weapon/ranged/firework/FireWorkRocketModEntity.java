package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.firework;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class FireWorkRocketModEntity extends FireworkRocketEntity {
    public FireWorkRocketModEntity(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }

    protected abstract void rocketHitEntity(EntityHitResult result);
    protected abstract void rocketHitBlock(BlockHitResult blockHitResult);

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        rocketHitEntity(hitResult);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        rocketHitBlock(hitResult);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
    }
}