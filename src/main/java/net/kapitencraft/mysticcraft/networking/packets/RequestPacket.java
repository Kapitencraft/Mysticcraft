package net.kapitencraft.mysticcraft.networking.packets;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.custom.ModRegistries;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestPacket<T, K> implements ModPacket {
    private final short requestId;
    private final IRequestable<T, K> provider;
    private final K value;

    public RequestPacket(short requestId, IRequestable<T, K> provider, K value) {
        this.requestId = requestId;
        this.provider = provider;
        this.value = value;
    }

    public RequestPacket(FriendlyByteBuf buf) {
        this.requestId = buf.readShort();
        this.provider = getRequestable(buf.readUtf());
        this.value = provider.readRequest(buf);
    }

    public static <T, K> IRequestable<T, K> getRequestable(String id) {
        ResourceLocation location = new ResourceLocation(id);
        IRequestable<T, K> requestable = (IRequestable<T, K>) ModRegistries.REQUESTABLES_REGISTRY.getValue(location);
        if (requestable == null) throw new IllegalStateException("unable to read requestable for key '" + id + "'");
        return requestable;
    }

    public static <T, K> String saveRequestable(IRequestable<T, K> requestable) {
        ResourceLocation location = ModRegistries.REQUESTABLES_REGISTRY.getKey(requestable);
        if (location == null) throw new IllegalStateException("can not send request without valid requestable");
        return location.toString();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeShort(this.requestId);
        buf.writeUtf(saveRequestable(this.provider));
        this.provider.writeRequest(this.value, buf);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
                if (player != null) {
                    try {
                        ModMessages.sendToClientPlayer(new RequestDataPacket<>(this.requestId, this.provider, this.provider.pack(this.value, player)), player);
                    } catch (Exception e) {
                        MysticcraftMod.LOGGER.warn(Markers.REQUESTS, "unable to handle Request Packet of provider '{}': {}", ModRegistries.REQUESTABLES_REGISTRY.getKey(this.provider), e.getMessage());
                    }
                }
        });
        return true;
    }
}
