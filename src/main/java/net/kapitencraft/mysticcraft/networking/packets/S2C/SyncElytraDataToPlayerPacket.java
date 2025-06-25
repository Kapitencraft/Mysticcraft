package net.kapitencraft.mysticcraft.networking.packets.S2C;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.kap_lib.io.network.S2C.capability.SyncCapabilityToPlayerPacket;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class SyncElytraDataToPlayerPacket extends SyncCapabilityToPlayerPacket<Pair<ElytraData, Integer>, IElytraData> {
    protected SyncElytraDataToPlayerPacket(List<Pair<ElytraData, Integer>> data) {
        super(data);
    }

    public SyncElytraDataToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public static SyncElytraDataToPlayerPacket fromPlayer(ServerPlayer player) {
        return SyncCapabilityToPlayerPacket.createPacket(player, CapabilityHelper.ELYTRA, SyncElytraDataToPlayerPacket::new);
    }

    @Override
    protected Capability<IElytraData> getCapability() {
        return CapabilityHelper.ELYTRA;
    }

    @Override
    protected FriendlyByteBuf.Reader<Pair<ElytraData, Integer>> getReader() {
        return buf -> new Pair<>(buf.readEnum(ElytraData.class), buf.readInt());
    }

    @Override
    protected FriendlyByteBuf.Writer<Pair<ElytraData, Integer>> getWriter() {
        return (buf, pair) -> {
            buf.writeEnum(pair.getFirst());
            buf.writeInt(pair.getSecond());
        };
    }
}
