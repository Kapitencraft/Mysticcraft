package net.kapitencraft.mysticcraft.block.tree.grower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public abstract class AbstractSizedTreeGrower extends AbstractTreeGrower {
    private final int size;

    protected AbstractSizedTreeGrower(int size) {
        this.size = size;
    }

    @Override
    public boolean growTree(ServerLevel pLevel, ChunkGenerator pGenerator, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        for(int i = 0; i > -size; --i) {
            for(int j = 0; j > -size; --j) {
                if (isSizedSapling(pState, pLevel, pPos, i, j, this.size)) {
                    return this.placeSized(pLevel, pGenerator, pPos, pState, pRandom, i, j);
                }
            }
        }


        return super.growTree(pLevel, pGenerator, pPos, pState, pRandom);
    }

    private boolean placeSized(ServerLevel pLevel, ChunkGenerator pGenerator, BlockPos pPos, BlockState pState, RandomSource pRandom, int pBranchX, int pBranchY) {
        ResourceKey<ConfiguredFeature<?, ?>> resourcekey = this.getConfiguredSizedFeature(pRandom);
        if (resourcekey == null) {
            return false;
        } else {
            Holder<ConfiguredFeature<?, ?>> holder = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(resourcekey).orElse((Holder.Reference<ConfiguredFeature<?, ?>>)null);
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(pLevel, pRandom, pPos, holder);
            holder = event.getFeature();
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.DENY) return false;
            if (holder == null) {
                return false;
            } else {
                ConfiguredFeature<?, ?> configuredfeature = holder.value();
                BlockState blockstate = Blocks.AIR.defaultBlockState();
                for (int x = 0; x < this.size; x++) {
                    for (int y = 0; y < this.size; y++) {
                        pLevel.setBlock(pPos.offset(pBranchX + x, 0, pBranchY + y), blockstate, 4);
                    }
                }
                if (configuredfeature.place(pLevel, pGenerator, pRandom, pPos.offset(pBranchX, 0, pBranchY))) {
                    return true;
                } else {
                    for (int x = 0; x < this.size; x++) {
                        for (int y = 0; y < this.size; y++) {
                            pLevel.setBlock(pPos.offset(pBranchX + x, 0, pBranchY + y), pState, 4);
                        }
                    }
                    return false;
                }
            }
        }
    }

    protected abstract ResourceKey<ConfiguredFeature<?,?>> getConfiguredSizedFeature(RandomSource pRandom);

    //i'm honestly baffled that vanilla hardcoded each position and didn't for-loop it
    public static boolean isSizedSapling(BlockState pBlockUnder, BlockGetter pLevel, BlockPos pPos, int pXOffset, int pZOffset, int size) {
        Block block = pBlockUnder.getBlock();
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                if (!pLevel.getBlockState(pPos.offset(pXOffset + x, 0, pZOffset + z)).is(block)) return false;
            }
        }
        return true;
    }
}
