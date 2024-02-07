package net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts;

import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public abstract class SyncCapabilityPacket<T extends ModCapability<T, K>, K extends ICapability<T>> implements ModPacket {
    protected final Map<Integer, T> capForSlotId;

    public SyncCapabilityPacket(Map<Integer, T> capForSlotId) {
        this.capForSlotId = capForSlotId;
    }

    public SyncCapabilityPacket(FriendlyByteBuf buf) {
        this.capForSlotId = buf.readMap(FriendlyByteBuf::readInt, getReader());
    }

    public abstract FriendlyByteBuf.Writer<T> getWriter();
    public abstract FriendlyByteBuf.Reader<T> getReader();
    public abstract Capability<K> getCapability();

    public abstract String getLogId();
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(capForSlotId, FriendlyByteBuf::writeInt, getWriter());
    }
}