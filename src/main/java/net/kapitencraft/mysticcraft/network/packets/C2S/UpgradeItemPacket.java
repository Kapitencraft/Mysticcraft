package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UpgradeItemPacket implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, UpgradeItemPacket> STREAM_CODEC = StreamCodec.unit(new UpgradeItemPacket());
    public static final Type<UpgradeItemPacket> TYPE = new Type<>(MysticcraftMod.res("upgrade_item"));

    public UpgradeItemPacket() {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof ReforgeAnvilMenu menu) {
                menu.upgrade();
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
