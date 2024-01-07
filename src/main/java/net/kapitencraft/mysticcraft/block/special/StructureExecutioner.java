package net.kapitencraft.mysticcraft.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface StructureExecutioner extends GameMasterBlock {

    void execute(BlockState state, BlockPos pos, ServerLevel serverLevel);

    Type getType();

    enum Type {
        DIRECT,
        AFTER_COMPLETE
    }
}
