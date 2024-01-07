package net.kapitencraft.mysticcraft.block.gemstone;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GemstoneCrystal extends GemstoneBlock {
    private static final EnumProperty<Size> SIZE = EnumProperty.create("size", Size.class);


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SIZE, BlockStateProperties.FACING);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = getItem(state, ModBlocks.GEMSTONE_CRYSTAL::getItem);
        stack.getOrCreateTag().putString("Size", state.getValue(SIZE).getSerializedName());
        return stack;
    }

    @Override
    public @NotNull BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(BlockStateProperties.FACING, p_152034_.rotate(p_152033_.getValue(BlockStateProperties.FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(BlockStateProperties.FACING)));
    }

    public static Size getSize(ItemStack stack) {
        return Size.CODEC.byName(stack.getOrCreateTag().getString("Size"), Size.SMALL);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(SIZE)) {
            case SMALL -> Blocks.SMALL_AMETHYST_BUD.getShape(state, getter, pos, context);
            case MEDIUM -> Blocks.MEDIUM_AMETHYST_BUD.getShape(state, getter, pos, context);
            case LARGE -> Blocks.LARGE_AMETHYST_BUD.getShape(state, getter, pos, context);
            case CLUSTER -> Blocks.AMETHYST_CLUSTER.getShape(state, getter, pos, context);
        };
    }

    public boolean canSurvive(BlockState p_152026_, LevelReader p_152027_, BlockPos p_152028_) {
        Direction direction = p_152026_.getValue(BlockStateProperties.FACING);
        BlockPos blockpos = p_152028_.relative(direction.getOpposite());
        return p_152027_.getBlockState(blockpos).isFaceSturdy(p_152027_, blockpos, direction);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        Player player = context.getPlayer();
        if (player == null || state == null) return state;
        ItemStack source = player.getItemInHand(context.getHand());
        return state.setValue(BlockStateProperties.FACING, context.getClickedFace()).setValue(SIZE, getSize(source));
    }

    @Override
    public void addBlock(LootContext context, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(context.getParam(LootContextParams.BLOCK_STATE), ModBlocks.GEMSTONE_CRYSTAL::getItem));
    }

    public static class Item extends GemstoneBlock.Item {
        public Item() {
            super(ModBlocks.GEMSTONE_CRYSTAL.getBlock());
        }

        @Override
        public @NotNull Component getName(@NotNull ItemStack stack) {
            return Component.translatable("gemstone_crystal." + getSize(stack).getSerializedName() + ".name", IGemstoneItem.getGemstone(stack).getDispName());
        }
    }

    public enum Size implements StringRepresentable {
        CLUSTER("cluster"),
        LARGE("large"),
        MEDIUM("medium"),
        SMALL("small");

        public static final EnumCodec<Size> CODEC = StringRepresentable.fromEnum(Size::values);

        private final String name;

        Size(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}