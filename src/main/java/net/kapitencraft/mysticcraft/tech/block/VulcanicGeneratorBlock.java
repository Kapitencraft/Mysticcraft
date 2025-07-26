package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.VulcanicGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VulcanicGeneratorBlock extends BaseEntityBlock {

    public VulcanicGeneratorBlock() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VulcanicGeneratorBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof VulcanicGeneratorBlockEntity blockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), blockEntity, pPos);
                return InteractionResult.CONSUME;
            }
            throw new IllegalStateException("Our Container provider is missing!");
        }
        return InteractionResult.SUCCESS;
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
