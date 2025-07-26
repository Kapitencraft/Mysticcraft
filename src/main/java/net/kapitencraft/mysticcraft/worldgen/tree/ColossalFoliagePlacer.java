package net.kapitencraft.mysticcraft.worldgen.tree;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.registry.ModFoliagePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class ColossalFoliagePlacer extends FoliagePlacer {
    public static final Codec<ColossalFoliagePlacer> CODEC = Codec.unit(ColossalFoliagePlacer::new);

    public ColossalFoliagePlacer() {
        super(ConstantInt.of(4), ConstantInt.of(0));
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return ModFoliagePlacers.COLOSSAL.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader pLevel, @NotNull FoliageSetter pBlockSetter, @NotNull RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        fillSphere(pAttachment.pos(), pConfig.foliageProvider, pBlockSetter, pRandom, pLevel, 3);
    }

    private static void fillSphere(BlockPos center, BlockStateProvider trunkProvider, FoliageSetter setter, RandomSource pRandom, LevelSimulatedReader pLevel, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    int dx = pos.getX() - center.getX();
                    int dy = pos.getY() - center.getY();
                    int dz = pos.getZ() - center.getZ();
                    if (Mth.sqrt(dx * dx + dy * dy + dz * dz) <= radius + .25 && !setter.isSet(pos) && pLevel.isStateAtPosition(pos, BlockState::canBeReplaced)) {
                        setter.set(pos, trunkProvider.getState(pRandom, pos));
                    }
                }
            }
        }
    }


    @Override
    public int foliageHeight(@NotNull RandomSource pRandom, int pHeight, @NotNull TreeConfiguration pConfig) {
        return 4;
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
