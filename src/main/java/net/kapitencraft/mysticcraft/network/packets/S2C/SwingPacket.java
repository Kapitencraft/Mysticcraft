package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.helpers.ExtraStreamCodecs;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SwingPacket(InteractionHand hand, int entityId) implements CustomPacketPayload {
    public static final Type<SwingPacket> TYPE = new Type<>(MysticcraftMod.res("swing"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, SwingPacket> STREAM_CODEC = StreamCodec.composite(
            ExtraStreamCodecs.enumCodec(InteractionHand.values()), SwingPacket::hand,
            ByteBufCodecs.INT, SwingPacket::entityId,
            SwingPacket::new
    );

    @SuppressWarnings("all")
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                living.swing(hand);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
