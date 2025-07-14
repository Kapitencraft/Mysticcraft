package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.tech.block.entity.ManaPortBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class ManaPortBlock extends BaseEntityBlock {
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public ManaPortBlock() {
        super(Properties.copy(Blocks.AMETHYST_BLOCK).pushReaction(PushReaction.BLOCK));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace().getOpposite();
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(direction));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ManaPortBlockEntity(pPos, pState);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (neighbor.equals(pos.relative(state.getValue(FACING)))) {
            ((ManaPortBlockEntity) level.getBlockEntity(pos)).checkAttached();
        }
        super.onNeighborChange(state, level, pos, neighbor);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        ((ManaPortBlockEntity) pLevel.getBlockEntity(pPos)).notifyRemoved();
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
