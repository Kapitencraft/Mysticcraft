package net.kapitencraft.mysticcraft.tech.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AbstractTurretBlockEntity extends BlockEntity {
    protected final AABB checkArea; //TODO un-finalize when upgrades are added
    protected Entity target;

    public AbstractTurretBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, double radius) {
        super(pType, pPos, pBlockState);
        this.checkArea = new AABB(pPos.getX() - radius, pPos.getY() - radius, pPos.getZ() - radius, pPos.getX() + radius, pPos.getY() + radius, pPos.getZ() + radius);
    }

    protected void updateTarget() {
        if (this.target != null) {
            if (!checkArea.intersects(this.target.getBoundingBox())) {
                this.unselectTarget();
            }
        }
        if (this.target == null && this.level != null) {
            this.selectTarget();
        }
    }

    /**
     * called when no target could be found and the turret has no target
     */
    protected void unselectTarget() {
    }

    protected void selectTarget() {
    }
}
