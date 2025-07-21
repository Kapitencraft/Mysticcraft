package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.RemoveManaDistributionNetworkElementPacket;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ManaPortBlockEntity extends BlockEntity implements IManaStorage {
    private ManaDistributionNetwork network;
    private ManaDistributionNetwork.Node node;
    private boolean attachChecked = false;

    public ManaPortBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MANA_PORT.get(), pPos, pBlockState);
    }

    public void setNetwork(ManaDistributionNetwork network) {
        this.network = network;
    }

    @Override
    public void setLevel(Level pLevel) {
        super.setLevel(pLevel);
        reloadNetwork();
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        Level level = this.level;
        if (level == null) return 0;
        this.checkAttached();
        int original = maxReceive;
        for (ManaDistributionNetwork.Node n : this.network.getNodes()) {
            if (n != this.node) {
                IManaStorage attached = n.getAttached(level);
                if (attached != null && attached.canReceive()) maxReceive -= attached.receiveMana(maxReceive, simulate);
            }
        }
        return original - maxReceive;
    }

    public void checkAttached() {
        if (!this.attachChecked && level != null) {
            BlockPos pos = this.worldPosition;
            BlockState state = level.getBlockState(pos);
            if (level.getBlockEntity(pos.relative(state.getValue(BlockStateProperties.FACING))) instanceof IManaStorage storage) {
                network.attach(pos, storage);
            }
            this.attachChecked = true;
        }
    }

    public void updateAttached() {
        if (this.level != null) {
            BlockPos pos = this.worldPosition;
            BlockState state = level.getBlockState(pos);
            if (level.getBlockEntity(pos.relative(state.getValue(BlockStateProperties.FACING))) instanceof IManaStorage storage) {
                network.attach(pos, storage);
            } else network.detach(pos);
        }
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        int original = maxExtract;
        Level level = this.level;
        if (level == null) return 0;
        this.checkAttached();
        for (ManaDistributionNetwork.Node n : this.network.getNodes()) {
            if (n != this.node) {
                IManaStorage attached = n.getAttached(level);
                if (attached != null && attached.canExtract()) maxExtract -= attached.extractMana(maxExtract, simulate);
            }
        }
        return original - maxExtract;
    }

    @Override
    public int getManaStored() {
        return 0;
    }

    @Override
    public int getMaxManaStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return (this.network != null || reloadNetwork()) && this.network.getNodes().size() > 1;
    }

    @Override
    public boolean canReceive() {
        return (this.network != null || reloadNetwork()) && this.network.getNodes().size() > 1;
    }

    @Override
    public void setMana(int mana) {
    }

    private boolean reloadNetwork() {
        if (this.level != null) {
            this.network = DistributionNetworkManager.get(level).getOrCreateNetwork(this.getBlockPos(), ManaDistributionNetwork.Node.Type.PORT);
            this.node = this.network.getNode(this.worldPosition);
            return true;
        }
        return false;
    }

    public void notifyRemoved() {
        if (this.level != null) {
            DistributionNetworkManager.get(this.level).remove(this.worldPosition, this.network);
            ModMessages.sendToAllConnectedPlayers(p -> new RemoveManaDistributionNetworkElementPacket(this.worldPosition), (ServerLevel) this.level);
        }
    }
}
