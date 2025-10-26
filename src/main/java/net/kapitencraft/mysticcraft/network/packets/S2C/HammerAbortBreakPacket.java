package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record HammerAbortBreakPacket(BlockPos pos, Direction direction) implements CustomPacketPayload {
    public static final Type<HammerAbortBreakPacket> TYPE = new Type<>(MysticcraftMod.res("hammer_abort_break"));
    public static final StreamCodec<RegistryFriendlyByteBuf, HammerAbortBreakPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, HammerAbortBreakPacket::pos,
            Direction.STREAM_CODEC, HammerAbortBreakPacket::direction,
            HammerAbortBreakPacket::new
    );

    public HammerAbortBreakPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readEnum(Direction.class));
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> HammerItem.abort(pos, direction));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
