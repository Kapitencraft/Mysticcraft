package net.kapitencraft.mysticcraft.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ModPacket {

    void toBytes(FriendlyByteBuf buf);

    boolean handle(Supplier<NetworkEvent.Context> sup);


}
