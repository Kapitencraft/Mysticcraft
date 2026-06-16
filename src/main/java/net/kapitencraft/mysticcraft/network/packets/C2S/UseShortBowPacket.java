package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UseShortBowPacket implements CustomPacketPayload {
    public static final UseShortBowPacket INSTANCE = new UseShortBowPacket();
    public static final StreamCodec<RegistryFriendlyByteBuf, UseShortBowPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<UseShortBowPacket> TYPE = new Type<>(MysticcraftMod.res("use_short_bow"));

    public UseShortBowPacket() {
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(()-> {
            Player player = context.player();
            if (player.getMainHandItem().getItem() instanceof ShortBowItem shortBowItem) {
                shortBowItem.releaseUsing(player.getMainHandItem(), player.level(), player, -1);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
