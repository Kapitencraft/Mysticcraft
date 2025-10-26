package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.rpg.classes.ClientClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateClassProgressionPacket(int level, float xp) implements CustomPacketPayload {
    public static final Type<UpdateClassProgressionPacket> TYPE = new Type<>(MysticcraftMod.res("update_class_progression"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateClassProgressionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateClassProgressionPacket::level,
            ByteBufCodecs.FLOAT, UpdateClassProgressionPacket::xp,
            UpdateClassProgressionPacket::new
    );

    public void handle(IPayloadContext sup) {
        sup.enqueueWork(() -> {
            ClientClass clientClass = ClientClass.getInstance();
            clientClass.setLevel(this.level);
            clientClass.setXp(this.xp);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
