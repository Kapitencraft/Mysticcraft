package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.firework;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class NapalmRocketEntity extends FireWorkRocketModEntity {
    public static final int EXPLOSION_SIZE = 10;
    public NapalmRocketEntity(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }
    @Override
    protected void rocketHitEntity(EntityHitResult result) {
        Vec3 pos = result.getLocation();
        BlockPos blockPos = new BlockPos(pos);
        Entity hit = result.getEntity();
        Level level = hit.level;
        int rangePerDirection = EXPLOSION_SIZE / 2;
        hit.hurt(DamageSource.IN_FIRE, 10);
        hit.setSecondsOnFire(20);
        for (int x = blockPos.getX() - rangePerDirection; x < blockPos.getX() + rangePerDirection; x++) {
            for (int y = blockPos.getY() - rangePerDirection; y < blockPos.getY() + rangePerDirection; y++) {
                for (int z = blockPos.getZ() - rangePerDirection; z < blockPos.getZ() + rangePerDirection; z++) {
                    BlockPos curPos = new BlockPos(x, y, z);
                    if (curPos.closerThan(blockPos, rangePerDirection)) {
                        BlockState state = level.getBlockState(curPos);
                        if (state.canOcclude()) {
                            level.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

    }
    @Override
    protected void rocketHitBlock(BlockHitResult blockHitResult) {

    }
}
