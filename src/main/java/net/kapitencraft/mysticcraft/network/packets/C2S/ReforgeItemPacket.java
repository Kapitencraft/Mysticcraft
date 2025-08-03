package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReforgeItemPacket implements SimplePacket {
    private final ResourceLocation reforgeId;

    public ReforgeItemPacket(ResourceLocation reforgeId) {
        this.reforgeId = reforgeId;
    }

    public ReforgeItemPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(reforgeId);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.containerMenu instanceof ReforgeAnvilMenu menu) {
                menu.reforgeForId(reforgeId, player);
            }
        });
    }
}
