package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseShortBowPacket implements SimplePacket {

    public UseShortBowPacket() {
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        context.enqueueWork(()-> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            if (player.getMainHandItem().getItem() instanceof ShortBowItem shortBowItem) {
                shortBowItem.releaseUsing(player.getMainHandItem(), player.level(), player, -1);
            }
        });
    }
}
