package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.item_stat.IItemStatHandler;
import net.kapitencraft.mysticcraft.item.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts.SyncCapabilityToPlayerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public class SyncItemStatDataToPlayerPacket extends SyncCapabilityToPlayerPacket<ItemStatCapability, IItemStatHandler> {
    public SyncItemStatDataToPlayerPacket(Map<Integer, ItemStatCapability> capForSlotId) {
        super(capForSlotId);
    }

    protected SyncItemStatDataToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public FriendlyByteBuf.Writer<ItemStatCapability> getWriter() {
        return ItemStatCapability::write;
    }

    @Override
    public FriendlyByteBuf.Reader<ItemStatCapability> getReader() {
        return ItemStatCapability::read;
    }

    @Override
    public Capability<IItemStatHandler> getCapability() {
        return CapabilityHelper.ITEM_STAT;
    }

    @Override
    public String getLogId() {
        return "Item-Stat";
    }
}
