package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.MagicFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicFurnaceBlock extends BaseEntityBlock {
    public MagicFurnaceBlock() {
        super(Properties.copy(Blocks.FURNACE));
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.LIT);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            NetworkHooks.openScreen(((ServerPlayer)pPlayer), (MagicFurnaceBlockEntity) pLevel.getBlockEntity(pPos), pPos);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MagicFurnaceBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MAGIC_FURNACE.get(), MagicFurnaceBlockEntity::tick);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            ((MagicFurnaceBlockEntity) pLevel.getBlockEntity(pPos)).drops();
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}