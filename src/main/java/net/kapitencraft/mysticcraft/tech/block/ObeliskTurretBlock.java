package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.ObeliskTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ObeliskTurretBlock extends BaseEntityBlock {
    public ObeliskTurretBlock() {
        super(Properties.copy(Blocks.AMETHYST_BLOCK));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ObeliskTurretBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.OBELISK_TURRET.get(), ObeliskTurretBlockEntity::tick);
    }
}
