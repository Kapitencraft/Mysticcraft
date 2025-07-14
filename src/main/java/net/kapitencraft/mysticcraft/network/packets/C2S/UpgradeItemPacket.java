package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpgradeItemPacket implements SimplePacket {

    public UpgradeItemPacket() {

    }

    public void toBytes(FriendlyByteBuf buf) {
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
