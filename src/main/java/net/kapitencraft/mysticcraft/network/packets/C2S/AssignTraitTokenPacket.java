package net.kapitencraft.mysticcraft.network.packets.C2S;

import io.netty.buffer.ByteBuf;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.rpg.traits.Traits;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record AssignTraitTokenPacket(Traits.Type traitType) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, AssignTraitTokenPacket> STREAM_CODEC = Traits.Type.STREAM_CODEC.map(AssignTraitTokenPacket::new, AssignTraitTokenPacket::traitType);

    public static final Type<AssignTraitTokenPacket> TYPE = new Type<>(MysticcraftMod.res("assign_trait_token"));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Traits data = player.getData(ModAttachmentTypes.TRAITS);
            data.tryUpdate(traitType, player);
        });
    }
}
