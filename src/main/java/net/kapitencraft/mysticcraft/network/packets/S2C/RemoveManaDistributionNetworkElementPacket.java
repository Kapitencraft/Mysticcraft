package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record RemoveManaDistributionNetworkElementPacket(BlockPos pos) implements SimplePacket {

    public RemoveManaDistributionNetworkElementPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            DistributionNetworkManager manager = DistributionNetworkManager.getClient();
            manager.remove(pos, null);
        });
    }
}
