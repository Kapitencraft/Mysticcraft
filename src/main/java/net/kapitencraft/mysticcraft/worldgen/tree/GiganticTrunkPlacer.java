package net.kapitencraft.mysticcraft.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.registry.ModTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GiganticTrunkPlacer extends TrunkPlacer {
    public static final Codec<GiganticTrunkPlacer> CODEC = RecordCodecBuilder.create(pInstance -> pInstance.group(
            Codec.INT.fieldOf("base_height").forGetter(g -> g.baseHeight),
            Codec.INT.fieldOf("height_rand_a").forGetter(g -> g.heightRandA),
            Codec.INT.fieldOf("height_rand_b").forGetter(g -> g.heightRandB)
    ).apply(pInstance, GiganticTrunkPlacer::new));

    public GiganticTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.GIANT.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        BlockPos center = pPos.offset(2, 0, 2);
        for (int i = 0; i < pFreeTreeHeight; i++) {
            this.fillLayer(center.offset(0, i, 0), pConfig.trunkProvider, pBlockSetter, pRandom, 2 + Mth.clamp((32 / (i + 1)) / 4, 0, 3));
        }

        return List.of();
    }

    public void fillLayer(BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource source, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = center.offset(x, 0, z);
                int dx = pos.getX() - center.getX();
                int dz = pos.getZ() - center.getZ();
                if (Mth.sqrt(dx * dx + dz * dz) <= radius + .25) blockSetter.accept(pos, trunkProvider.getState(source, pos));
            }
        }
    }
}
