package net.kapitencraft.mysticcraft.network.packets.C2S;

import io.netty.buffer.ByteBuf;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SelectSpellSlotPacket(int slot) implements CustomPacketPayload {
    public static final Type<SelectSpellSlotPacket> TYPE = new Type<>(MysticcraftMod.res("select_spell_slot"));
    public static final StreamCodec<ByteBuf, SelectSpellSlotPacket> STREAM_CODEC = ByteBufCodecs.INT.map(SelectSpellSlotPacket::new, SelectSpellSlotPacket::slot);

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().setData(ModAttachmentTypes.SELECTED_SPELL_SLOT, slot);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
