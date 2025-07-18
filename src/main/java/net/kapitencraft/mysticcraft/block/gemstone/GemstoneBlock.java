package net.kapitencraft.mysticcraft.block.gemstone;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.ModBlockStateProperties;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GemstoneBlock extends HalfTransparentBlock {
    public static final float VERY_LOW_STRENGHT = 4;
    public static final float LOW_STRENGHT = 5;
    public static final float LOW_MEDIUM_STRENGHT = 6;
    public static final float MEDIUM_STRENGHT = 7;
    public static final float HIGH_MEDIUM_STRENGHT = 8;
    public static final float HIGH_STRENGHT = 9;
    public static final float VERY_HIGH_STRENGHT = 10;

    public GemstoneBlock() {
        super(Properties.copy(Blocks.AMETHYST_CLUSTER).requiresCorrectToolForDrops().noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(ModBlockStateProperties.GEMSTONE_TYPE, GemstoneType.EMPTY));
    }

    @Override
    public int getLightBlock(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ModBlockStateProperties.GEMSTONE_TYPE);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        pParams.withDynamicDrop(MysticcraftMod.res("gemstone_item"), pOutput -> this.addItem(pParams.getParameter(LootContextParams.BLOCK_STATE), pOutput));
        pParams.withDynamicDrop(MysticcraftMod.res("gemstone_block"), pOutput -> this.addBlock(pParams.getParameter(LootContextParams.BLOCK_STATE), pOutput));
        return super.getDrops(pState, pParams);
    }

    public static GemstoneType getType(BlockState state) {
        return state.getValue(ModBlockStateProperties.GEMSTONE_TYPE);
    }

    public static int getColor(BlockState state, BlockAndTintGetter ignored0, BlockPos ignored, int tintIndex) {
        return tintIndex == 0 ? getType(state).getColour() : -1;
    }

    public static <T extends net.minecraft.world.item.Item & IGemstoneItem> ItemStack getItem(BlockState state, Supplier<T> supplier) {
        return IGemstoneItem.createData(GemstoneType.Rarity.ROUGH, getType(state), supplier);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        Player player = context.getPlayer();
        if (player == null) return state;
        ItemStack place = player.getItemInHand(context.getHand());
        return state.setValue(ModBlockStateProperties.GEMSTONE_TYPE, IGemstoneItem.getGemstone(place));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getItem(state, ModBlocks.GEMSTONE_BLOCK::getItem);
    }

    public void addItem(BlockState state, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(state, ModItems.GEMSTONE));
    }
    public void addBlock(BlockState state, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(state, ModBlocks.GEMSTONE_BLOCK::getItem));
    }

    public static class Item extends BlockItem implements IGemstoneItem {
        public Item() {
            this(ModBlocks.GEMSTONE_BLOCK.get());
        }
        protected Item(Block block) {
            super(block, MiscHelper.rarity(Rarity.RARE));
        }

        @Override
        public @NotNull Component getName(@NotNull ItemStack stack) {
            return Component.translatable("gemstone_block.name", IGemstoneItem.getGemstone(stack).getDispName());
        }
    }
}