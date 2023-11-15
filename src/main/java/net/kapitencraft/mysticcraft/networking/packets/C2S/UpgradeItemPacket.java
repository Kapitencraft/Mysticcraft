package net.kapitencraft.mysticcraft.networking.packets.C2S;

import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgingAnvilMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpgradeItemPacket {

    public UpgradeItemPacket() {
    }

    public UpgradeItemPacket(FriendlyByteBuf ignored) {
    }

    public void toBytes(FriendlyByteBuf ignored) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.containerMenu instanceof ReforgingAnvilMenu menu) {
                menu.upgrade();
            }
        });
        return false;
    }
}
