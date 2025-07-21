package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveManaDistributionNetworkElementPacket implements SimplePacket {
    private final BlockPos pos;

    public RemoveManaDistributionNetworkElementPacket(BlockPos pos) {
        this.pos = pos;
    }

    public RemoveManaDistributionNetworkElementPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            DistributionNetworkManager manager = DistributionNetworkManager.getClient();
            manager.remove(pos, null);
        });
        return true;
    }
}
