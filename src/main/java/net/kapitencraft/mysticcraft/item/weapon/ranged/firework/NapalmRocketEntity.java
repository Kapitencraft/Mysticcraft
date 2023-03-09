package net.kapitencraft.mysticcraft.item.weapon.ranged.firework;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NapalmRocketEntity extends FireWorkRocketModEntity {
    public NapalmRocketEntity(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }
    @Override
    protected void rocketHitEntity(Entity hit, Vec3 hitLoc) {

    }
    @Override
    protected void rocketHitBlock(Direction direction, BlockPos pos, boolean miss, boolean inside, Vec3 hitLoc) {

    }
}
