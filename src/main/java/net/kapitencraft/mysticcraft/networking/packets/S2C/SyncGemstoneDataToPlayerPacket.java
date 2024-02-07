package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneCapability;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts.SyncCapabilityToPlayerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public class SyncGemstoneDataToPlayerPacket extends SyncCapabilityToPlayerPacket<GemstoneCapability, IGemstoneHandler> {

    public SyncGemstoneDataToPlayerPacket(Map<Integer, GemstoneCapability> capForSlotId) {
        super(capForSlotId);
    }

    public SyncGemstoneDataToPlayerPacket(FriendlyByteBuf buf) {
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

    public static SyncGemstoneDataToPlayerPacket fromPlayer(ServerPlayer serverPlayer) {
        return SyncCapabilityToPlayerPacket.createFromCapability(SyncGemstoneDataToPlayerPacket::new, serverPlayer, CapabilityHelper.GEMSTONE);
    }

    public static SyncGemstoneDataToPlayerPacket simple(int id, GemstoneCapability capability) {
        return new SyncGemstoneDataToPlayerPacket(Map.of(id, capability));
    }

    @Override
    public String getLogId() {
        return "Gemstone";
    }
}
