package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraCapability;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts.SyncCapabilityToPlayerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

public class SyncElytraDataToPlayerPacket extends SyncCapabilityToPlayerPacket<ElytraCapability, IElytraData> {
    public SyncElytraDataToPlayerPacket(Map<Integer, ElytraCapability> capForSlotId) {
        super(capForSlotId);
    }

    public SyncElytraDataToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public static SyncElytraDataToPlayerPacket fromPlayer(ServerPlayer player) {
        return SyncCapabilityToPlayerPacket.createFromCapability(SyncElytraDataToPlayerPacket::new, player, CapabilityHelper.ELYTRA);
    }

    @Override
    public FriendlyByteBuf.Writer<ElytraCapability> getWriter() {
        return ElytraCapability::write;
    }

    @Override
    public FriendlyByteBuf.Reader<ElytraCapability> getReader() {
        return ElytraCapability::read;
    }

    @Override
    public Capability<IElytraData> getCapability() {
        return CapabilityHelper.ELYTRA;
    }

    @Override
    public String getLogId() {
        return "Elytra";
    }
}
