package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.RemoveManaDistributionNetworkElementPacket;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        ModMessages.sendToAllConnectedPlayers(p -> new RemoveManaDistributionNetworkElementPacket(pPos), (ServerLevel) pLevel);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
