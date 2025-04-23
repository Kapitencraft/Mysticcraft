package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MangaticSlimeBlock extends SlimeBlock {
    public MangaticSlimeBlock() {
        super(Properties.copy(Blocks.SLIME_BLOCK));
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel level, BlockPos pos, @NotNull RandomSource source) {
        if (level.getBlockState(pos.relative(Direction.DOWN)).getBlock() == Blocks.END_STONE) {
            level.setBlock(pos.relative(Direction.DOWN), ModBlocks.MANGATIC_STONE.getBlock().defaultBlockState(), 3);
        }
    }
}
