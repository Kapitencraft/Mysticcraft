package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetTargetPriorityPacket(BlockPos pos, int slot, int index) implements CustomPacketPayload {
    public static final Type<SetTargetPriorityPacket> TYPE = new Type<>(MysticcraftMod.res("set_target_priority"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetTargetPriorityPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetTargetPriorityPacket::pos,
            ByteBufCodecs.INT, SetTargetPriorityPacket::slot,
            ByteBufCodecs.INT, SetTargetPriorityPacket::index,
            SetTargetPriorityPacket::new
    );

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            BlockEntity blockEntity = sender.level().getBlockEntity(pos);
            if (blockEntity instanceof AbstractTurretBlockEntity abstractTurretBlockEntity) {
                abstractTurretBlockEntity.setTargetPriority(slot, index);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
