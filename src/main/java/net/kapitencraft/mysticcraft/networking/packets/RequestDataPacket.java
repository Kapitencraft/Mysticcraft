package net.kapitencraft.mysticcraft.networking.packets;

import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestDataPacket<T, K> implements ModPacket {
    private final IRequestable<T, K> provider;
    private final short id;
    private final T data;

    public RequestDataPacket(FriendlyByteBuf buf) {
        this.id = buf.readShort();
        this.provider = RequestPacket.getRequestable(buf.readUtf());
        this.data = this.provider.getFromNetwork(buf);
    }

    public short getId() {
        return id;
    }

    public RequestDataPacket(short requestId, IRequestable<T, K> provider, T data) {
        this.id = requestId;
        this.provider = provider;
        this.data = data;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeShort(this.id);
        buf.writeUtf(RequestPacket.saveRequestable(this.provider));
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        context.enqueueWork(() -> MysticcraftClient.getInstance().handler.accordPackageReceive(this));
        return false;
    }

    public T getData() {
        return data;
    }
}
