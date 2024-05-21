package net.kapitencraft.mysticcraft.block.special;

import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.BlockGetter;
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
    //TODO fix renderer clipping threw blocks

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
        if (Mth.nextInt(pRandom, 0, 1000) > 750) GemstoneGrowth.growCrystal(pLevel, pPos, 1, GemstoneGrowth.DEFAULT_MAIN_CHANCE, pRandom);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        ItemStack clickItem = pContext.getItemInHand();
        CompoundTag tag = clickItem.getTagElement("GemstoneData");
        if (!clickItem.is(ModBlocks.GEMSTONE_SEED.getItem()) || tag == null) throw new IllegalStateException("Do not overwrite Item");
        MaterialType type = MaterialType.CODEC.byName(tag.getString("Material"), MaterialType.STONE);
        GemstoneType gemType = GemstoneType.CODEC.byName(tag.getString("GemId"), GemstoneType.RUBY);
        return defaultBlockState()
                .setValue(BlockStateProperties.FACING, pContext.getClickedFace())
                .setValue(ModBlockProperties.GEMSTONE_TYPE, gemType)
                .setValue(ModBlockProperties.STONE_TYPE, type);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return GemstoneBlock.getItem(state, ModBlocks.GEMSTONE_SEED::getItem);
    }

    public static final class Item extends BlockItem implements IGemstoneItem {
        public Item() {
            super(ModBlocks.GEMSTONE_SEED.getBlock(), MiscHelper.rarity(Rarity.EPIC));
        }

        @Override
        public @NotNull Component getName(@NotNull ItemStack pStack) {
            return Component.translatable("gemstone_seed.name", IGemstoneItem.getGemstone(pStack).getDispName());
        }

        public static ItemStack createData(GemstoneType type, MaterialType materialType) {
            ItemStack stack = IGemstoneItem.createData(GemstoneType.Rarity.EMPTY, type, ModBlocks.GEMSTONE_SEED::getItem);
            stack.getOrCreateTag().getCompound("GemstoneData").putString("Material", materialType.getSerializedName());
            return stack;
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

    public static MaterialType getType(ItemStack stack) {
        return MaterialType.CODEC.byName(stack.getOrCreateTagElement("GemstoneData").getString("Material"), MaterialType.STONE);
    }
}