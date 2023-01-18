package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ManaLiquidBlock extends LiquidBlock {

    public ManaLiquidBlock() {
        super(ModFluids.SOURCE_MANA_FLUID, BlockBehaviour.Properties.copy(Blocks.LAVA));
    }


    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel serverLevel, BlockPos pos, @NotNull RandomSource source) {
        if (serverLevel.getBlockState(pos.below()).getBlock() == Blocks.SLIME_BLOCK && source.nextInt(1, 1000) == 1000) {
            serverLevel.setBlock(pos.below(), ModBlocks.MANGATIC_SLIME.getBlock().defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState p_54732_) {
        return true;
    }
}
