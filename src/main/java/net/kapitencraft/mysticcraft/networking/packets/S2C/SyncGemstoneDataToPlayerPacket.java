package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.kap_lib.io.network.S2C.capability.SyncCapabilityToPlayerPacket;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class SyncGemstoneDataToPlayerPacket extends SyncCapabilityToPlayerPacket<List<GemstoneSlot>, IGemstoneHandler> {
    protected SyncGemstoneDataToPlayerPacket(List<List<GemstoneSlot>> data) {
        super(data);
    }

    public SyncGemstoneDataToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public static SyncGemstoneDataToPlayerPacket fromPlayer(ServerPlayer player) {
        return SyncCapabilityToPlayerPacket.createPacket(player, CapabilityHelper.GEMSTONE, SyncGemstoneDataToPlayerPacket::new);
    }

    @Override
    protected Capability<IGemstoneHandler> getCapability() {
        return CapabilityHelper.GEMSTONE;
    }

    @Override
    protected FriendlyByteBuf.Reader<List<GemstoneSlot>> getReader() {
        return buf -> buf.readList(GemstoneSlot::fromNw);
    }

    @Override
    protected FriendlyByteBuf.Writer<List<GemstoneSlot>> getWriter() {
        return (buf, gemstoneSlots) -> buf.writeCollection(gemstoneSlots, GemstoneSlot::toNw);
    }
}
