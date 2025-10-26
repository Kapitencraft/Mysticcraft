package net.kapitencraft.mysticcraft.tech.block;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.entity.AbstractMenuBlock;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.VulcanicGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VulcanicGeneratorBlock extends AbstractMenuBlock {
    public static final MapCodec<VulcanicGeneratorBlock> CODEC = simpleCodec(VulcanicGeneratorBlock::new);

    private VulcanicGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public VulcanicGeneratorBlock() {
        this(Properties.ofFullCopy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VulcanicGeneratorBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.VULCANIC_GENERATOR.get(), GenericFueledGeneratorBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            ((GenericFueledGeneratorBlockEntity) pLevel.getBlockEntity(pPos)).drops();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
