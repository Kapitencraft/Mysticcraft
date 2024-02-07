package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneCapability;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts.SyncCapabilityToBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public class SyncGemstoneDataToBlockPacket extends SyncCapabilityToBlockPacket<GemstoneCapability, IGemstoneHandler> {

    public SyncGemstoneDataToBlockPacket(BlockPos pos, Map<Integer, GemstoneCapability> capForSlotId) {
        super(pos, capForSlotId);
    }

    public SyncGemstoneDataToBlockPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public FriendlyByteBuf.Writer<GemstoneCapability> getWriter() {
        return GemstoneHelper::writeCapability;
    }

    @Override
    public FriendlyByteBuf.Reader<GemstoneCapability> getReader() {
        return GemstoneHelper::readCapability;
    }

    @Override
    public Capability<IGemstoneHandler> getCapability() {
        return CapabilityHelper.GEMSTONE;
    }

    @Override
    public String getLogId() {
        return "Gemstone";
    }
}
