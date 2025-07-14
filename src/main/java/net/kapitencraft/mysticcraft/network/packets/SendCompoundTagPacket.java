package net.kapitencraft.mysticcraft.network.packets;

import net.kapitencraft.kap_lib.helpers.IOHelper;
import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendCompoundTagPacket implements SimplePacket {
    private final CompoundTag toSend;
    private final int entityIdToReceive;
    private final boolean toServer;

    public SendCompoundTagPacket(CompoundTag toSend, int entityIdToReceive, boolean toServer) {
        this.toSend = toSend;
        this.entityIdToReceive = entityIdToReceive;
        this.toServer = toServer;
    }

    public SendCompoundTagPacket(FriendlyByteBuf buf) {
        this(IOHelper.fromString(buf.readUtf()), buf.readInt(), buf.readBoolean());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(IOHelper.fromCompoundTag(toSend));
        buf.writeInt(entityIdToReceive);
        buf.writeBoolean(toServer);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        if (toServer) {
            context.enqueueWork(()-> {
                ServerPlayer serverPlayer = context.getSender();
                if (serverPlayer != null) {
                    ServerLevel level = serverPlayer.serverLevel();
                    Entity entity = level.getEntity(entityIdToReceive);
                    if (entity != null) IOHelper.injectCompoundTag(entity, toSend);
                }
            });
        } else {
            context.enqueueWork(()-> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null) {
                    Entity entity = level.getEntity(entityIdToReceive);
                    if (entity != null) IOHelper.injectCompoundTag(entity, toSend);
                }
            });
        }
        return false;
    }


    public enum Usage {
        INJECT_TO_ENTITY
    }
}
