package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class DistributionNetworkBlock extends Block {
    private final ManaDistributionNetwork.Node.Type type;

    public DistributionNetworkBlock(ManaDistributionNetwork.Node.Type type) {
        super(Properties.copy(Blocks.AMETHYST_BLOCK).pushReaction(PushReaction.BLOCK));
        this.type = type;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        DistributionNetworkManager manager = DistributionNetworkManager.get(pLevel);
        if (manager != null) manager.add(pPos, type);
    }

    public ManaDistributionNetwork.Node.Type getType() {
        return type;
    }
}
