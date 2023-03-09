package net.kapitencraft.mysticcraft.item.weapon.ranged.firework;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class FireWorkRocketModEntity extends FireworkRocketEntity {
    public FireWorkRocketModEntity(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }

    protected abstract void rocketHitEntity(Entity hit, Vec3 hitLoc);
    protected abstract void rocketHitBlock(Direction direction, BlockPos pos, boolean miss, boolean inside, Vec3 hitLoc);

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37069_) {
        super.onHitBlock(p_37069_);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
    }
}