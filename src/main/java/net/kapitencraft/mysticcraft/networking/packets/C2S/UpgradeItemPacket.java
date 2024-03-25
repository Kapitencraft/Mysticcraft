package net.kapitencraft.mysticcraft.networking.packets.C2S;

import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpgradeItemPacket implements ModPacket {

    public UpgradeItemPacket() {
    }

    public void toBytes(FriendlyByteBuf ignored) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.containerMenu instanceof ReforgeAnvilMenu menu) {
                menu.upgrade();
            }
        });
        return false;
    }
}
