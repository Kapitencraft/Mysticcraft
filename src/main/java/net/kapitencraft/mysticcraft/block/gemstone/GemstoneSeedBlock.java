package net.kapitencraft.mysticcraft.block.gemstone;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.block.ModBlockStateProperties;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.capability.gemstone.ItemGemstoneData;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GemstoneSeedBlock extends Block {

    public GemstoneSeedBlock() {
        super(Properties.ofFullCopy(Blocks.DIAMOND_BLOCK));
        this.registerDefaultState(this.getStateDefinition().any().setValue(ModBlockStateProperties.GEMSTONE_TYPE, GemstoneType.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ModBlockStateProperties.GEMSTONE_TYPE, ModBlockStateProperties.STONE_TYPE, BlockStateProperties.FACING);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (Mth.nextInt(pRandom, 0, 1000) > 750) GemstoneGrowth.growCrystal(pLevel, pPos, 1, GemstoneGrowth.DEFAULT_MAIN_CHANCE, pRandom);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        ItemStack clickItem = pContext.getItemInHand();
        ItemGemstoneData data = clickItem.get(ModDataComponentTypes.ITEM_GEMSTONE_DATA);
        MaterialType type = clickItem.get(ModDataComponentTypes.GEMSTONE_SEED_MATERIAL);
        if (!clickItem.is(ModBlocks.GEMSTONE_SEED.getItem()) || data == null || type == null) throw new IllegalStateException("Do not overwrite Item");
        GemstoneType gemType = data.type();
        return defaultBlockState()
                .setValue(BlockStateProperties.FACING, pContext.getClickedFace())
                .setValue(ModBlockStateProperties.GEMSTONE_TYPE, gemType)
                .setValue(ModBlockStateProperties.STONE_TYPE, type);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return Item.createData(state.getValue(ModBlockStateProperties.GEMSTONE_TYPE), state.getValue(ModBlockStateProperties.STONE_TYPE));
    }

    public static final class Item extends BlockItem implements IGemstoneItem {
        public Item() {
            super(ModBlocks.GEMSTONE_SEED.get(), MiscHelper.rarity(Rarity.EPIC));
        }

        @Override
        public @NotNull Component getName(@NotNull ItemStack pStack) {
            return Component.translatable("gemstone_seed.name", IGemstoneItem.getGemstone(pStack).getDispName());
        }

        public static ItemStack createData(GemstoneType type, MaterialType materialType) {
            ItemStack stack = IGemstoneItem.createData(GemstoneType.Rarity.EMPTY, type, ModBlocks.GEMSTONE_SEED::getItem);
            stack.set(ModDataComponentTypes.GEMSTONE_SEED_MATERIAL, materialType);
            return stack;
        }
    }

    public enum MaterialType implements StringRepresentable {
        STONE("stone", ()-> Blocks.STONE),
        END_STONE("end_stone", () -> Blocks.END_STONE),
        DEEPSLATE("deepslate", ()-> Blocks.DEEPSLATE),
        NETHERRACK("netherrack", ()-> Blocks.NETHERRACK);

        public static final EnumCodec<MaterialType> CODEC = StringRepresentable.fromEnum(MaterialType::values);

        private final String name;
        private final Supplier<Block> block;

        MaterialType(String name, Supplier<Block> block) {
            this.name = name;
            this.block = block;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public Block getBlock() {
            return block.get();
        }
    }

    public static MaterialType getType(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.GEMSTONE_SEED_MATERIAL, MaterialType.STONE);
    }
}