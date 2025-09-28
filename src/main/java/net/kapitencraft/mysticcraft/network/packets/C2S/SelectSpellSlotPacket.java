package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.spell.capability.PlayerSpells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectSpellSlotPacket implements SimplePacket {
    private final int slot;

    public SelectSpellSlotPacket(int slot) {
        this.slot = slot;
    }

    public SelectSpellSlotPacket(FriendlyByteBuf buf) {
        this(buf.readShort());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeShort(this.slot);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            PlayerSpells spells = PlayerSpells.get(sender);
            spells.setSelectedSlot(slot);
        });
    }
}
