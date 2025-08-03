package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.block.entity.pedestal.AbstractPedestalBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.pedestal.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        AbstractPedestalBlockEntity entity = (AbstractPedestalBlockEntity) pLevel.getBlockEntity(pPos);
        ItemStack item = entity.getItem();
        if (!ItemStack.isSameItemSameTags(stack, item)) {
            entity.setItem(stack);
            pPlayer.setItemInHand(pHand, item);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        ((AbstractPedestalBlockEntity) pLevel.getBlockEntity(pPos)).drops();
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
