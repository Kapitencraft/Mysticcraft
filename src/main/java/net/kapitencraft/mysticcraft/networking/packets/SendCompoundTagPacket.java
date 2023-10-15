package net.kapitencraft.mysticcraft.networking.packets;

import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendCompoundTagPacket implements ModPacket {
    private final CompoundTag toSend;
    private final int entityIdToReceive;
    private final boolean toServer;

    public SendCompoundTagPacket(CompoundTag toSend, int entityIdToReceive, boolean toServer) {
        this.toSend = toSend;
        this.entityIdToReceive = entityIdToReceive;
        this.toServer = toServer;
    }

    public SendCompoundTagPacket(FriendlyByteBuf buf) {
        this(TagHelper.fromString(buf.readUtf()), buf.readInt(), buf.readBoolean());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(TagHelper.fromCompoundTag(toSend));
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        if (toServer) {
            NetworkEvent.Context context = sup.get();
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                ServerLevel level = serverPlayer.getLevel();
                Entity entity = level.getEntity(entityIdToReceive);
                if (entity != null) TagHelper.injectCompoundTag(entity, toSend);
            }
        } else {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(entityIdToReceive);
                if (entity != null) TagHelper.injectCompoundTag(entity, toSend);
            }
        }
        return false;
    }


    public enum Usage {
        INJECT_TO_ENTITY
    }
}
