package net.kapitencraft.mysticcraft.block.special;

import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
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

    public enum MaterialType implements StringRepresentable {
        STONE("stone", ()-> Blocks.STONE),
        END_STONE("end_stone", () -> Blocks.END_STONE),
        DEEPSLATE("deepslate", ()-> Blocks.DEEPSLATE),
        NETHERRACK("netherrack", ()-> Blocks.NETHERRACK);

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