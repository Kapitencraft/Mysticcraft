package net.kapitencraft.mysticcraft.block.special;

import net.kapitencraft.mysticcraft.api.DoubleMap;
import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GemstoneSeedBlock extends Block {
    //TODO create Renderer

    public GemstoneSeedBlock() {
        super(Properties.copy(Blocks.DIAMOND_BLOCK));
        this.registerDefaultState(this.getStateDefinition().any().setValue(ModBlockProperties.GEMSTONE_TYPE, GemstoneType.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ModBlockProperties.GEMSTONE_TYPE, ModBlockProperties.STONE_TYPE, BlockStateProperties.FACING);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        GemstoneGrowth.growCrystal(pLevel, pPos, 1, GemstoneGrowth.DEFAULT_MAIN_CHANCE, pRandom);
    }

    public static final class Item extends BlockItem {
        public Item() {
            super(ModBlocks.GEMSTONE_SEED.getBlock(), MiscHelper.rarity(Rarity.EPIC));
        }

        public static DoubleMap<MaterialType, GemstoneType, ItemStack> makeContent() {
            DoubleMap<MaterialType, GemstoneType, ItemStack> doubleMap = DoubleMap.create();
            for (MaterialType type : MaterialType.values()) {
                for (GemstoneType type1 : GemstoneType.values()) {
                    ItemStack stack = new ItemStack(ModBlocks.GEMSTONE_SEED.getItem());
                    CompoundTag tag = stack.getOrCreateTag();
                    tag.putString("Material", type.getSerializedName());
                    tag.putString("Gemstone", type1.getSerializedName());
                    doubleMap.put(type, type1, stack);
                }
            }
            return doubleMap;
        }

        @Override
        protected BlockState getPlacementState(BlockPlaceContext pContext) {
            ItemStack clickItem = pContext.getItemInHand();
            CompoundTag tag = clickItem.getTag();
            if (!clickItem.is(ModBlocks.GEMSTONE_SEED.getItem()) || tag == null) throw new IllegalStateException("Do not overwrite Item");
            MaterialType type = MaterialType.CODEC.byName(tag.getString("Material"), MaterialType.STONE);
            GemstoneType gemType = GemstoneType.CODEC.byName(tag.getString("Gemstone"), GemstoneType.RUBY);
            return this.getBlock().defaultBlockState()
                    .setValue(BlockStateProperties.FACING, pContext.getNearestLookingDirection())
                    .setValue(ModBlockProperties.GEMSTONE_TYPE, gemType)
                    .setValue(ModBlockProperties.STONE_TYPE, type);
        }
    }

    public enum MaterialType implements StringRepresentable {
        STONE("stone", ()-> Blocks.STONE),
        END_STONE("end_stone", () -> Blocks.END_STONE),
        DEEPSLATE("deepslate", ()-> Blocks.DEEPSLATE),
        NETHERRACK("netherrack", ()-> Blocks.NETHERRACK);

        private static final EnumCodec<MaterialType> CODEC = StringRepresentable.fromEnum(MaterialType::values);

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
}