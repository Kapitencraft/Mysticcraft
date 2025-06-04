package net.kapitencraft.mysticcraft.networking.packets.S2C;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.kap_lib.io.network.S2C.capability.SyncCapabilityToPlayerPacket;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

public class SyncElytraDataToPlayerPacket extends SyncCapabilityToPlayerPacket<Pair<ElytraData, Integer>, IElytraData> {
    protected SyncElytraDataToPlayerPacket(List<Pair<ElytraData, Integer>> data) {
        super(data);
    }

    public SyncElytraDataToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    public static SyncGemstoneDataToPlayerPacket fromPlayer(ServerPlayer player) {
        Inventory inventory = player.getInventory();
        List<List<GemstoneSlot>> slots = new ArrayList<>(inventory.getContainerSize());
        for (int[] i = new int[] {0}; i[0] < inventory.getContainerSize(); i[0]++) {
            inventory.getItem(i[0]).getCapability(CapabilityHelper.GEMSTONE).ifPresent(iGemstoneHandler -> slots.set(i[0], iGemstoneHandler.getData()));
        }
        return new SyncGemstoneDataToPlayerPacket(slots);
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
