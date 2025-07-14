package net.kapitencraft.mysticcraft.tech;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class DistributionNetworkManager extends SavedData {
    private static final DistributionNetworkManager clientManager = new DistributionNetworkManager();

    public static DistributionNetworkManager get(Level level) {
        if (level.isClientSide()) {
            return clientManager;
        } else return ((ServerLevel) level).getDataStorage().computeIfAbsent(DistributionNetworkManager::load, DistributionNetworkManager::new, "distribution_networks");
    }

    private final List<ManaDistributionNetwork> networks = new ArrayList<>();

    public static DistributionNetworkManager getClient() {
        return clientManager;
    }

    public static void applyClient(DistributionNetworkManager manager) {
        clientManager.networks.clear();
        clientManager.networks.addAll(manager.getNetworks());
    }


    public ManaDistributionNetwork add(BlockPos pPos, ManaDistributionNetwork.Node.Type type) {
        ManaDistributionNetwork network = new ManaDistributionNetwork();
        network.add(pPos, type);
        this.networks.add(network);
        this.setDirty();
        return network;
    }

    public void connect(ManaDistributionNetwork network1, ManaDistributionNetwork network2, BlockPos pos1, BlockPos pos2, Level level) {
        if (network1 != network2) {
            if (network1.size() > network2.size()) {
                network1.addAll(network2);
                network2.notifyMove(network1, level);
                network1.connect(pos1, pos2);
                this.networks.remove(network2);
            } else {
                network2.addAll(network1);
                network1.notifyMove(network2, level);
                network2.connect(pos1, pos2);
                this.networks.remove(network1);
            }
        } else network1.connect(pos1, pos2);
        this.setDirty();
    }

    public ManaDistributionNetwork getOrCreateNetwork(BlockPos pos, ManaDistributionNetwork.Node.Type type) {
        ManaDistributionNetwork network = getNetwork(pos);
        if (network == null) {
            network = this.add(pos, type);
        }
        return network;
    }

    @Contract(pure = true)
    public ManaDistributionNetwork getNetwork(BlockPos pos) {
        for (ManaDistributionNetwork network : this.networks) {
            if (network.contains(pos)) return network;
        }
        return null;
    }


    public static DistributionNetworkManager load(CompoundTag tag) {
        List<ManaDistributionNetwork> networkList = new ArrayList<>();
        ListTag listTag = tag.getList("networks", Tag.TAG_LIST);
        for (int i = 0; i < listTag.size(); i++) {
            networkList.add(ManaDistributionNetwork.create(listTag.getList(i)));
        }
        DistributionNetworkManager manager = new DistributionNetworkManager();
        manager.networks.addAll(networkList);
        return manager;
    }


    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        for (ManaDistributionNetwork network : networks) {
            listTag.add(network.save());
        }
        pCompoundTag.put("networks", listTag);
        return pCompoundTag;
    }

    public List<ManaDistributionNetwork> getNetworks() {
        return networks;
    }

    public void remove(BlockPos pos, ManaDistributionNetwork network) {
        network.remove(pos);
        if (network.empty()) this.networks.remove(network);
        this.setDirty();
    }
}