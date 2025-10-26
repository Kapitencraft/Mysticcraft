package net.kapitencraft.mysticcraft.tech.block;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.entity.AbstractMenuBlock;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.PrismaticGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PrismaticGeneratorBlock extends AbstractMenuBlock {
    private static final MapCodec<PrismaticGeneratorBlock> CODEC = MapCodec.unit(PrismaticGeneratorBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public PrismaticGeneratorBlock() {
        super(Properties.ofFullCopy(Blocks.LODESTONE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PrismaticGeneratorBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PRISMATIC_GENERATOR.get(), PrismaticGeneratorBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            ((GenericFueledGeneratorBlockEntity) pLevel.getBlockEntity(pPos)).drops();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
