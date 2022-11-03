package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemstoneGrinderBlock extends BaseEntityBlock {
    public GemstoneGrinderBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD).requiresCorrectToolForDrops().sound(SoundType.WOOD));
    }


    /* BLOCK ENTITY */

    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }


    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean flag) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof GemstoneGrinderBlockEntity grinderBlockEntity) {
                grinderBlockEntity.drops();
            }
        }
        super.onRemove(state, level, pos, newState, flag);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState p_49432_, Level p_49433_, @NotNull BlockPos p_49434_, @NotNull Player p_49435_, @NotNull InteractionHand p_49436_, @NotNull BlockHitResult p_49437_) {
        if (p_49433_.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = p_49433_.getBlockEntity(p_49434_);
            if (blockentity instanceof GemstoneGrinderBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) p_49435_,  (GemstoneGrinderBlockEntity)blockentity);
                p_49435_.awardStat(Stats.INTERACT_WITH_BEACON);
            }

            return InteractionResult.sidedSuccess(true);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GemstoneGrinderBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.GEMSTONE_GRINDER.get(), GemstoneGrinderBlockEntity::tick);
    }

    @Override
    public boolean triggerEvent(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }

}
