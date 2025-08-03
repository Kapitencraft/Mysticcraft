package net.kapitencraft.mysticcraft.block.entity.pedestal;

import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends AbstractPedestalBlockEntity {
    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL.get(), pPos, pBlockState);
    }

    public void shrinkItem() {
        this.getItem().shrink(1);
        this.setChanged();
    }
}
