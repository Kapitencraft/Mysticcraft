package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.gui.menu.UpgradableBEMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ToggleUpgradeVisibilityPacket implements CustomPacketPayload {
    public static final ToggleUpgradeVisibilityPacket INSTANCE = new ToggleUpgradeVisibilityPacket();
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleUpgradeVisibilityPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<ToggleUpgradeVisibilityPacket> TYPE = new Type<>(MysticcraftMod.res("toggle_update_visibility"));

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            if (sender.containerMenu instanceof UpgradableBEMenu<?> menu) {
                menu.setShowUpgrades(!menu.doesShowUpgrades());
            } else {
                MysticcraftMod.LOGGER.warn("attempted to toggle upgrade visibility without valid menu");
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
