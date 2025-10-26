package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncManaDistributionNetworksPacket(DistributionNetworkManager manager) implements CustomPacketPayload {
    public static final Type<SyncManaDistributionNetworksPacket> TYPE = new Type<>(MysticcraftMod.res("sync_mana_dist_networks"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncManaDistributionNetworksPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, value) ->
                    buffer.writeNbt(value.manager().saveDirectly(new CompoundTag())),
            buffer ->
                    new SyncManaDistributionNetworksPacket(DistributionNetworkManager.loadDirectly(buffer.readNbt()))
    );

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> DistributionNetworkManager.applyClient(this.manager));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
