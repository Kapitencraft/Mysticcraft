package net.kapitencraft.mysticcraft.block;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AbstractPedestalBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.pedestal.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);

    private PedestalBlock(Properties properties) {
        super(properties);
    }

    public PedestalBlock() {
        this(Properties.ofFullCopy(Blocks.STONE).noOcclusion());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        AbstractPedestalBlockEntity entity = (AbstractPedestalBlockEntity) level.getBlockEntity(pos);
        ItemStack pedestalItem = entity.getItem();
        if (!pedestalItem.isEmpty()) {
            if (stack.isEmpty() || ItemStack.isSameItemSameComponents(stack, pedestalItem)) {
                player.setItemInHand(hand, pedestalItem);
                entity.setItem(ItemStack.EMPTY);
            }
        } else {
            player.setItemInHand(hand, entity.insertItem(stack));
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
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

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
