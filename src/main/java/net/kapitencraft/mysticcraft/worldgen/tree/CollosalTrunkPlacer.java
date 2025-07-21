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
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class CollosalTrunkPlacer extends TrunkPlacer {
    public static final Codec<CollosalTrunkPlacer> CODEC = RecordCodecBuilder.create(pInstance -> pInstance.group(
            Codec.INT.fieldOf("base_height").forGetter(g -> g.baseHeight),
            Codec.INT.fieldOf("height_rand_a").forGetter(g -> g.heightRandA),
            Codec.INT.fieldOf("height_rand_b").forGetter(g -> g.heightRandB)
    ).apply(pInstance, CollosalTrunkPlacer::new));

    public CollosalTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.COLLOSAL.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        BlockPos center = pPos.offset(2, 0, 2);
        int nextOffset = 8;
        for (int i = 0; i < pFreeTreeHeight; i++) {
            if (i == nextOffset) {
                int xOffset = pRandom.nextIntBetweenInclusive(-1, 1);
                int yOffset = pRandom.nextIntBetweenInclusive(-1, 1);
                center = center.offset(xOffset, 0, yOffset);
                nextOffset = i + pRandom.nextIntBetweenInclusive(8, 15);
            }
            fillHorizontalCircle(center.above(i), pConfig.trunkProvider, pBlockSetter, pRandom, 2 + Mth.clamp((32 / (i + 1)) / 4, 0, 3));
            float lastRot = 0;
            if (i > 10 && i < (pFreeTreeHeight - 10) && pRandom.nextDouble() < .35) {
                lastRot = (lastRot + (pRandom.nextFloat() + .5f) * (float) Math.PI) % ((float) Math.PI * 2);
                Vec2 offset = new Vec2(Mth.sin(lastRot), Mth.cos(lastRot));
                BranchBuilder builder = randomBuilder(pRandom);
                builder.createBranch(offset, center.above(i), pConfig.trunkProvider, pBlockSetter, pRandom, 10 + pRandom.nextInt(5), 1);
            }
        }

        center = center.above(pFreeTreeHeight);
        int iterations = 5 + pRandom.nextInt(10);
        for (int i = 0; i < iterations; i++) {
            float rot = (float) Math.PI * 2 / iterations * i;
            Vec2 direction = new Vec2(Mth.sin(rot), Mth.cos(rot));
            BranchBuilder builder = new TurningBranchBuilder(); // new LinearBranchBuilder(pRandom.nextFloat());
            builder.createBranch(direction, center, pConfig.trunkProvider, pBlockSetter, pRandom, 15 + pRandom.nextInt(10), 1);
        }

        return List.of();
    }

    private static BranchBuilder randomBuilder(RandomSource pRandom) {
        if (pRandom.nextFloat() < .5) return new TurningBranchBuilder();
        return new LinearBranchBuilder(pRandom.nextFloat() * 1.5f);
    }

    private static void fillSphere(BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    int dx = pos.getX() - center.getX();
                    int dy = pos.getY() - center.getY();
                    int dz = pos.getZ() - center.getZ();
                    if (Mth.sqrt(dx * dx + dy * dy + dz * dz) <= radius + .25) {
                        pBlockSetter.accept(pos, trunkProvider.getState(pRandom, pos));
                    }
                }
            }
        }
    }

    public static void fillHorizontalCircle(BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource source, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = center.offset(x, 0, z);
                int dx = pos.getX() - center.getX();
                int dz = pos.getZ() - center.getZ();
                if (Mth.sqrt(dx * dx + dz * dz) <= radius + .25) blockSetter.accept(pos, trunkProvider.getState(source, pos));
            }
        }
    }

    public static void fillVerticalCircle(BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource source, int radius) {
        for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = center.offset(0, y, z);
                int dy = pos.getY() - center.getY();
                int dz = pos.getZ() - center.getZ();
                if (Mth.sqrt(dy * dy + dz * dz) <= radius + .25) blockSetter.accept(pos, trunkProvider.getState(source, pos));
            }
        }
    }

    private interface BranchBuilder {
        void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int width, int radius);
    }

    /**
     * @param growth growth in height / block
     */
    private record LinearBranchBuilder(float growth) implements BranchBuilder {

        @Override
        public void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int maxWidth, int radius) {
            float height = 0;
            int yOffset = 0;
            for (int i = 0; i < maxWidth; i++) {
                int xOffset = (int) (direction.x * i);
                int zOffset = (int) (direction.y * i);
                fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                if (radius == 1 && i > 5 && pRandom.nextFloat() < .75f) {
                    BranchBuilder builder = new LinearBranchBuilder(pRandom.nextFloat() / 2 + .5f);
                    builder.createBranch(new Vec2((pRandom.nextFloat() - .5f) * 2, (pRandom.nextFloat() - .5f) * 2), origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, 5 + pRandom.nextInt(5), 0);
                }
                for (height += growth; height >= 1; height--) {
                    yOffset++;
                    fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                }
            }
        }
    }

    private static class TurningBranchBuilder implements BranchBuilder {

        @Override
        public void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int maxWidth, int radius) {
            int yOffset = 0;
            int sizeScale = 10 + pRandom.nextInt(15);
            int nextBranch = 5 + pRandom.nextInt(3);
            for (int i = 0; i < maxWidth; i++) {
                int xOffset = (int) (direction.x * i);
                int zOffset = (int) (direction.y * i);
                int y = i * sizeScale / (i + 10);
                fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                if (radius == 1 && i == nextBranch) {
                    BranchBuilder builder = new LinearBranchBuilder(pRandom.nextFloat() / 2 + .5f);
                    Vec2 dir = pRandom.nextFloat() < .5f ? new Vec2(-direction.y, direction.x) : new Vec2(direction.y, -direction.x);
                    builder.createBranch(dir, origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, 5 + pRandom.nextInt(5), 0);
                    nextBranch += 4 + pRandom.nextInt(4);
                }
                while (yOffset < y) {
                    yOffset++;
                    fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                }
            }
        }
    }
}
