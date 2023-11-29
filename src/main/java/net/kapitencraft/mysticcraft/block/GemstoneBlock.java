package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GemstoneBlock extends Block {
    private static final EnumProperty<GemstoneType> TYPE = EnumProperty.create("gemstone", GemstoneType.class);
    public static final double VERY_LOW_STRENGHT = 4;
    public static final double LOW_STRENGHT = 5;
    public static final double LOW_MEDIUM_STRENGHT = 6;
    public static final double MEDIUM_STRENGHT = 7;
    public static final double HIGH_MEDIUM_STRENGHT = 8;
    public static final double HIGH_STRENGHT = 9;
    public static final double VERY_HIGH_STRENGHT = 10;

    public GemstoneBlock() {
        super(Properties.of(Material.HEAVY_METAL).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops());
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, GemstoneType.ALMANDINE));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState p_60537_, LootContext.Builder builder) {
        builder.withDynamicDrop(MysticcraftMod.res("gemstone_item"), this::addItem);
        builder.withDynamicDrop(MysticcraftMod.res("gemstone_block"), this::addBlock);
        return super.getDrops(p_60537_, builder);
    }

    public static GemstoneType getType(BlockState state) {
        return state.getValue(TYPE);
    }

    public static int getColor(BlockState state, BlockAndTintGetter ignored, BlockPos ignored1, int ignored2) {
        return getType(state).getColour();
    }

    public ItemStack getItem(BlockState state, boolean block) {
        return IGemstoneItem.createData(GemstoneType.Rarity.ROUGH, getType(state), block);
    }


    public static void setType(BlockState state, GemstoneType type) {
        state.setValue(TYPE, type);
    }

    public void addItem(LootContext context, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(context.getParam(LootContextParams.BLOCK_STATE), false));
    }
    public void addBlock(LootContext context, Consumer<ItemStack> consumer) {
        consumer.accept(getItem(context.getParam(LootContextParams.BLOCK_STATE), true));
    }

    public static class Item extends BlockItem implements IGemstoneItem {
        public Item() {
            super(ModBlocks.GEMSTONE_BLOCK.getBlock(), MiscHelper.rarity(Rarity.RARE));
        }

        @Nullable
        @Override
        protected BlockState getPlacementState(BlockPlaceContext context) {
            BlockState state = getBlock().defaultBlockState();
            Player player = context.getPlayer();
            if (player == null) return state;
            ItemStack place = player.getItemInHand(context.getHand());
            state.setValue(TYPE, IGemstoneItem.getGemstone(place));
            return state;
        }
    }
}
