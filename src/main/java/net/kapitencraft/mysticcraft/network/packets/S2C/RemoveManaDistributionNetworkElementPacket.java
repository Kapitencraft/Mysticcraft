package net.kapitencraft.mysticcraft.network.packets.S2C;

import io.netty.buffer.ByteBuf;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RemoveManaDistributionNetworkElementPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<RemoveManaDistributionNetworkElementPacket> TYPE = new Type<>(MysticcraftMod.res("remove_distribution_element"));
    public static final StreamCodec<ByteBuf, RemoveManaDistributionNetworkElementPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(RemoveManaDistributionNetworkElementPacket::new, RemoveManaDistributionNetworkElementPacket::pos);

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            DistributionNetworkManager manager = DistributionNetworkManager.getClient();
            manager.remove(pos, null);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
