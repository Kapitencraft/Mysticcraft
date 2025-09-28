package net.kapitencraft.mysticcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * a block that grows only when the moon is right
 */
public class MoonphaseGrowingBlock extends Block {
    private final int moonphase;

    public MoonphaseGrowingBlock(Properties pProperties, int moonphase) {
        super(pProperties);
        this.moonphase = moonphase;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true; //apparently we do not get access to the level here, so we have to dispatch it to the random tick method. that's unfortunate
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.getMoonPhase() == moonphase && pRandom.nextDouble() < .01) {
            //increase state
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }
}
