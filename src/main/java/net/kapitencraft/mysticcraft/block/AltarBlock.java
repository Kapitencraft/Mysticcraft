package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarBlockEntity;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends PedestalBlock {

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(ModBlocks.PEDESTAL.getItem())) {
            AltarBlockEntity blockEntity = ((AltarBlockEntity) pLevel.getBlockEntity(pPos));
            for (BlockPos pedestalPosition : blockEntity.getPedestalPositions()) {
                BlockState state = pLevel.getBlockState(pedestalPosition);
                if (!state.is(ModBlocks.PEDESTAL.get()) && pLevel.getBlockState(pedestalPosition).canBeReplaced()) {
                    pLevel.setBlockAndUpdate(pedestalPosition, ModBlocks.PEDESTAL.get().defaultBlockState());
                    if (!InventoryHelper.isCreativeMode(pPlayer)) {
                        stack.shrink(1);
                        if (stack.isEmpty()) break;
                    }
                }
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR.get(), AltarBlockEntity::serverTick);
    }
}
