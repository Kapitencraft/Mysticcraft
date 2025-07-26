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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ColossalTrunkPlacer extends TrunkPlacer {
    public static final Codec<ColossalTrunkPlacer> CODEC = RecordCodecBuilder.create(pInstance -> pInstance.group(
            Codec.INT.fieldOf("base_height").forGetter(g -> g.baseHeight),
            Codec.INT.fieldOf("height_rand_a").forGetter(g -> g.heightRandA),
            Codec.INT.fieldOf("height_rand_b").forGetter(g -> g.heightRandB)
    ).apply(pInstance, ColossalTrunkPlacer::new));

    public ColossalTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.COLOSSAL.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader pLevel, @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter, @NotNull RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, @NotNull TreeConfiguration pConfig) {
        BlockPos center = pPos.offset(2, 0, 2);
        List<FoliagePlacer.FoliageAttachment> list = new ArrayList<>();
        center = verticalTrunk(8, pFreeTreeHeight, pRandom, center, pConfig.trunkProvider, pBlockSetter, list, 2, 3, 32);

        center = center.above(pFreeTreeHeight - 6);
        branch(pRandom, center, pConfig.trunkProvider, pBlockSetter, list, 1, 4, 3, 20, 10, 3, 4, 15, 10);

        center = center.above(6);
        int smallHeight = 15 + pRandom.nextInt(10);
        center = verticalTrunk(6, smallHeight, pRandom, center, pConfig.trunkProvider, pBlockSetter, list, 1, 1, 32);
        center = center.above(smallHeight - 6);
        branch(pRandom, center, pConfig.trunkProvider, pBlockSetter, list, 0, 3, 3, 10, 8, 4, 3, 8, 6);
        center = center.above(6);
        int smallestHeight = 6 + pRandom.nextInt(5);
        center = verticalTrunk(2, smallestHeight, pRandom, center, pConfig.trunkProvider, pBlockSetter, list, 0, 1, 8);
        list.add(new FoliagePlacer.FoliageAttachment(center.above(smallestHeight), 0, false));

        return list;
    }

    private static BlockPos verticalTrunk(int nextOffset, int height, RandomSource pRandom, BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, List<FoliagePlacer.FoliageAttachment> list, int radius, int maxOversize, int oversizeScale) {
        for (int i = 0; i < height; i++) {
            if (i == nextOffset) {
                int xOffset = pRandom.nextIntBetweenInclusive(-1, 1);
                int yOffset = pRandom.nextIntBetweenInclusive(-1, 1);
                center = center.offset(xOffset, 0, yOffset);
                nextOffset = i + pRandom.nextIntBetweenInclusive(8, 15);
            }
            fillHorizontalCircle(center.above(i), trunkProvider, pBlockSetter, pRandom, radius + Mth.clamp((oversizeScale / (i + 1)) / 4, 0, maxOversize));
            float lastRot = 0;
            if (radius > 1 && i > 10 && i < (height - 10) && pRandom.nextDouble() < .35) {
                lastRot = (lastRot + (pRandom.nextFloat() + .5f) * (float) Math.PI) % ((float) Math.PI * 2);
                Vec2 offset = new Vec2(Mth.sin(lastRot), Mth.cos(lastRot));
                BranchBuilder builder = randomBuilder(pRandom);
                builder.createBranch(offset, center.above(i), trunkProvider, pBlockSetter, pRandom, 10 + pRandom.nextInt(5), radius - 1, list, false);
            }
        }
        return center;
    }

    private static void branch(RandomSource pRandom, BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, List<FoliagePlacer.FoliageAttachment> list, int radius, int minLowIterations, int extraLowIterations, int minLowWidth, int extraLowWidth, int minHighIterations, int extraHighIterations, int minHighWidth, int extraHighWidth) {
        int lowIterations = minLowIterations + pRandom.nextInt(extraLowIterations);
        for (int i = 0; i < lowIterations; i++) {
            float rot = (float) Math.PI * 2f / lowIterations * i + (((float) Math.PI / 3.5f));
            Vec2 direction = new Vec2(Mth.sin(rot), Mth.cos(rot));
            BranchBuilder builder = new TurningBranchBuilder(8 + pRandom.nextInt(5)); // new LinearBranchBuilder(pRandom.nextFloat());
            builder.createBranch(direction, center, trunkProvider, pBlockSetter, pRandom, minLowWidth + pRandom.nextInt(extraLowWidth), radius, list, radius == 0);
        }
        int highIterations = minHighIterations + pRandom.nextInt(extraHighIterations);
        for (int i = 0; i < highIterations; i++) {
            float rot = (float) Math.PI * 2 / highIterations * i;
            Vec2 direction = new Vec2(Mth.sin(rot), Mth.cos(rot));
            BranchBuilder builder = randomHighBuilder(pRandom);
            builder.createBranch(direction, center, trunkProvider, pBlockSetter, pRandom, minHighWidth + pRandom.nextInt(extraHighWidth), radius, list, radius == 0);
        }
    }

    private static BranchBuilder randomBuilder(RandomSource pRandom) {
        if (pRandom.nextFloat() < .5) return new TurningBranchBuilder(10 + pRandom.nextInt(5));
        return new LinearBranchBuilder(pRandom.nextFloat() * 1.5f);
    }

    private static BranchBuilder randomHighBuilder(RandomSource pRandom) {
        if (pRandom.nextFloat() < .5) return new TurningBranchBuilder(25 + pRandom.nextInt(10));
        return new LinearBranchBuilder(pRandom.nextFloat() + .5f);
    }

    public static void fillSphere(BlockPos center, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int radius) {
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
        void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int width, int radius, List<FoliagePlacer.FoliageAttachment> list, boolean fork);
    }

    /**
     * @param growth growth in height / block
     */
    private record LinearBranchBuilder(float growth) implements BranchBuilder {

        @Override
        public void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int maxWidth, int radius, List<FoliagePlacer.FoliageAttachment> list, boolean fork) {
            float height = 0;
            int yOffset = 0;
            for (int i = 0; i < maxWidth; i++) {
                int xOffset = (int) (direction.x * i);
                int zOffset = (int) (direction.y * i);
                fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                if ((radius == 1 || fork) && i > 5 && pRandom.nextFloat() < .75f) {
                    BranchBuilder builder = new LinearBranchBuilder(pRandom.nextFloat() / 2 + .5f);
                    builder.createBranch(new Vec2((pRandom.nextFloat() - .5f) * 2, (pRandom.nextFloat() - .5f) * 2), origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, 5 + pRandom.nextInt(3), 0, list, radius == 1);
                }
                for (height += growth; height >= 1; height--) {
                    yOffset++;
                    fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                }
            }
            BlockPos tip = origin.offset((int) (direction.x * maxWidth), yOffset, (int) (direction.y * maxWidth));
            list.add(new FoliagePlacer.FoliageAttachment(tip, 0, false));
        }
    }

    private record TurningBranchBuilder(int sizeScale) implements BranchBuilder {

        @SuppressWarnings("SuspiciousNameCombination")
        @Override
        public void createBranch(Vec2 direction, BlockPos origin, BlockStateProvider trunkProvider, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int maxWidth, int radius, List<FoliagePlacer.FoliageAttachment> list, boolean fork) {
            int yOffset = 0;
            int nextBranch = 4 + pRandom.nextInt(3);
            for (int i = 0; i < maxWidth; i++) {
                int xOffset = (int) (direction.x * i);
                int zOffset = (int) (direction.y * i);
                int y = i * this.sizeScale / (i + 10);
                fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                if ((radius == 1 || fork) && i == nextBranch) {
                    BranchBuilder builder = new LinearBranchBuilder(pRandom.nextFloat() / 2 + .5f);
                    float f = pRandom.nextFloat();
                    Vec2 v1 = new Vec2(-direction.y, direction.x);
                    Vec2 v2 = new Vec2(direction.y, -direction.x);
                    List<Vec2> directions = f < .1 ? List.of(v1) : f < .9 ? List.of(v1, v2) : List.of(v2);
                    for (Vec2 dir : directions) {
                        builder.createBranch(dir, origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, 5 + pRandom.nextInt(5), 0, list, radius == 1);
                    }
                    nextBranch += 3 + pRandom.nextInt(3);
                }
                while (yOffset < y) {
                    yOffset++;
                    fillSphere(origin.offset(xOffset, yOffset, zOffset), trunkProvider, pBlockSetter, pRandom, radius);
                }
            }
            BlockPos tip = origin.offset((int) (direction.x * maxWidth), yOffset, (int) (direction.y * maxWidth));
            list.add(new FoliagePlacer.FoliageAttachment(tip, 0, false));
        }
    }
}
