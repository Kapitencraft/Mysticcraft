package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record SyncManaDistributionNetworksPacket(DistributionNetworkManager manager) implements SimplePacket {

    public SyncManaDistributionNetworksPacket(FriendlyByteBuf buf) {
        this(DistributionNetworkManager.load(Objects.requireNonNull(buf.readNbt(), "buf could not read NBT")));
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(this.manager.save(new CompoundTag()));
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(() -> DistributionNetworkManager.applyClient(this.manager));
    }
}
