package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarBlockEntity;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends PedestalBlock {

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ModBlocks.PEDESTAL.getItem())) {
            AltarBlockEntity blockEntity = ((AltarBlockEntity) level.getBlockEntity(pos));
            for (BlockPos pedestalPosition : blockEntity.getPedestalPositions()) {
                BlockState pedestalState = level.getBlockState(pedestalPosition);
                if (!pedestalState.is(ModBlocks.PEDESTAL.get()) && level.getBlockState(pedestalPosition).canBeReplaced()) {
                    level.setBlockAndUpdate(pedestalPosition, ModBlocks.PEDESTAL.get().defaultBlockState());
                    if (!InventoryHelper.isCreativeMode(player)) {
                        stack.shrink(1);
                    }
                    break;
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        pContext.getClickedPos();
        return super.getStateForPlacement(pContext);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR.get(), pLevel.isClientSide ? AltarBlockEntity::clientTick : AltarBlockEntity::serverTick);
    }
}
