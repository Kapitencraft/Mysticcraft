package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ObeliskTurretBlockEntity extends AbstractTurretBlockEntity {
    private int activeTicks;
    private int damage = 1;

    public ObeliskTurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.OBELISK_TURRET.get(), pPos, pBlockState, 6);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ObeliskTurretBlockEntity entity) {
        entity.updateTarget();
        if (entity.target != null) {
            if ((entity.activeTicks & 7) == 0 && !entity.target.hurt(pLevel.damageSources().source(ModDamageTypes.SCORCH), entity.damage)) {
                entity.unselectTarget();
            }
            if ((entity.activeTicks++ & 31) == 0) entity.damage <<= 1;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void selectTarget() {
        List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, checkArea, e -> e instanceof LivingEntity living && !living.isRemoved() && !living.isDeadOrDying());
        if (!entities.isEmpty()) this.target = entities.get(0);
    }

    @Override
    protected void unselectTarget() {
        damage = 1;
        activeTicks = 0;
        target = null;
    }
}
