package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncEssenceDataPacket implements ModPacket {
    private final EssenceHolder holder;

    public SyncEssenceDataPacket(EssenceHolder holder) {
        this.holder = holder;
    }

    public SyncEssenceDataPacket(FriendlyByteBuf buf) {
        this(new EssenceHolder(buf.readMap(buf1 -> buf1.readEnum(EssenceType.class), FriendlyByteBuf::readInt)));
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(holder.getContent(), FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeInt);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            localPlayer.getCapability(CapabilityHelper.ESSENCE).ifPresent(essenceHolder -> essenceHolder.copyFrom(holder));
        });
        return true;
    }
}
