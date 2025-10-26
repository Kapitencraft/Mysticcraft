package net.kapitencraft.mysticcraft.network.packets.C2S;

import io.netty.buffer.ByteBuf;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ReforgeItemPacket(ResourceLocation reforgeId) implements CustomPacketPayload {
    public static final Type<ReforgeItemPacket> TYPE = new Type<>(MysticcraftMod.res("reforge_item"));
    public static final StreamCodec<ByteBuf, ReforgeItemPacket> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(ReforgeItemPacket::new, ReforgeItemPacket::reforgeId);

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof ReforgeAnvilMenu menu) {
                menu.reforgeForId(reforgeId, (ServerPlayer) player);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
