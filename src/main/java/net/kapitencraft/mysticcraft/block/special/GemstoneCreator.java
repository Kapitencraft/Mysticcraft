package net.kapitencraft.mysticcraft.block.special;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GemstoneCreator extends Block implements StructureExecutioner {
    private static final int RANGE = 3;
    private static final int HEIGHT = 5;

    public GemstoneCreator() {
        super(Properties.copy(Blocks.JIGSAW));
    }


    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state1, boolean flag) {
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

    @Override
    public void execute(BlockState state, BlockPos pos, ServerLevel level) {
        GemstoneType type = createType(level, pos);
        BlockPos rangeOffset = new BlockPos(RANGE, HEIGHT, RANGE);
        List<BlockPos> allPos = MathHelper.between(pos.subtract(rangeOffset), pos.offset(rangeOffset.offset(1, 1, 1))).toList();
        Reference<Integer> ref = Reference.of(0);
        allPos.forEach(pos1 -> {
            BlockState state2 = level.getBlockState(pos1);
            try {
                GemstoneType type1 = GemstoneBlock.getType(state2);
                if (type1 == GemstoneType.EMPTY) {
                    level.setBlockAndUpdate(pos1, GemstoneBlock.setType(state2, type));
                    MathHelper.up1(ref);
                }
            } catch (IllegalArgumentException ignored) {}
        });
        MysticcraftMod.LOGGER.info("Changed " + ref.getValue() + " blocks");

    }

    @Override
    public Type getType() {
        return null;
    }
}
