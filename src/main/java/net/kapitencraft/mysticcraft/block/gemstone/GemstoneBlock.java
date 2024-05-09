package net.kapitencraft.mysticcraft.block.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneItem;
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
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GemstoneBlock extends HalfTransparentBlock {
    public static final double VERY_LOW_STRENGHT = 4;
    public static final double LOW_STRENGHT = 5;
    public static final double LOW_MEDIUM_STRENGHT = 6;
    public static final double MEDIUM_STRENGHT = 7;
    public static final double HIGH_MEDIUM_STRENGHT = 8;
    public static final double HIGH_STRENGHT = 9;
    public static final double VERY_HIGH_STRENGHT = 10;

    public GemstoneBlock() {
        super(Properties.of(Material.HEAVY_METAL).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(ModBlockProperties.GEMSTONE_TYPE, GemstoneType.ALMANDINE));
    }

    @Override
    public int getLightBlock(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ModBlockProperties.GEMSTONE_TYPE);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState p_60537_, LootContext.Builder builder) {
        builder.withDynamicDrop(MysticcraftMod.res("gemstone_item"), this::addItem);
        builder.withDynamicDrop(MysticcraftMod.res("gemstone_block"), this::addBlock);
        return super.getDrops(p_60537_, builder);
    }

    public static GemstoneType getType(BlockState state) {
        return state.getValue(ModBlockProperties.GEMSTONE_TYPE);
    }

    public static int getColor(BlockState state, BlockAndTintGetter ignored, BlockPos ignored1, int ignored2) {
        return getType(state).getColour();
    }

    public <T extends net.minecraft.world.item.Item & IGemstoneItem> ItemStack getItem(BlockState state, Supplier<T> supplier) {
        return IGemstoneItem.createData(GemstoneType.Rarity.ROUGH, getType(state), supplier);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        Player player = context.getPlayer();
        if (player == null) return state;
        ItemStack place = player.getItemInHand(context.getHand());
        return state.setValue(ModBlockProperties.GEMSTONE_TYPE, IGemstoneItem.getGemstone(place));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getItem(state, ModBlocks.GEMSTONE_BLOCK::getItem);
    }

    public void addItem(LootContext context, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(context.getParam(LootContextParams.BLOCK_STATE), ModItems.GEMSTONE));
    }
    public void addBlock(LootContext context, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(context.getParam(LootContextParams.BLOCK_STATE), ModBlocks.GEMSTONE_BLOCK::getItem));
    }

    public static class Item extends BlockItem implements IGemstoneItem {
        public Item() {
            this(ModBlocks.GEMSTONE_BLOCK.getBlock());
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
