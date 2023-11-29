package net.kapitencraft.mysticcraft.block.special;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GemstoneCreator extends Block implements GameMasterBlock {
    private static final int RANGE = 3;

    public GemstoneCreator() {
        super(Properties.copy(Blocks.JIGSAW));
    }


    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state1, boolean flag) {
        GemstoneType type = createType(level, pos);
        GemstoneBlock block = ModBlocks.GEMSTONE_BLOCK.getBlock();
        BlockState toPlace = block.defaultBlockState();
        GemstoneBlock.setType(toPlace, type);
        MysticcraftMod.sendInfo("loading creator!");
        MathHelper.forCube(new BlockPos(RANGE, RANGE, RANGE), (pos1) -> {
            if (level.getBlockState(pos1).getBlock() instanceof EmptyGemstoneBlock) {
                level.setBlockAndUpdate(pos1.offset(pos), toPlace);
            }
        });
    }


    private static GemstoneType createType(Level level, BlockPos pos) {
        List<GemstoneType> types = new ArrayList<>();
        Holder<Biome> biome = level.getBiome(pos.atY(100));
        if (biome.is(Biomes.OCEAN)) {
            types.add(GemstoneType.AQUAMARINE);
            types.add(GemstoneType.TURQUOISE);
        }
        if (biome.is(Biomes.FOREST)) {
            types.add(GemstoneType.CELESTINE);
        }
        if (biome.is(Tags.Biomes.IS_MOUNTAIN)) {
            types.add(GemstoneType.AMETHYST);
            types.add(GemstoneType.SAPPHIRE);
        }
        if (biome.is(Biomes.END_HIGHLANDS)) {
            types.add(GemstoneType.ALMANDINE);
        }
        if (biome.is(Biomes.BASALT_DELTAS)) {
            types.add(GemstoneType.JASPER);
        }
        if (level.dimension() == Level.OVERWORLD) {
            types.add(GemstoneType.RUBY);
        }
        return MathHelper.pickRandom(types);
    }
}
