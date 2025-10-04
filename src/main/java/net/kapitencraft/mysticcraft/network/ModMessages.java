package net.kapitencraft.mysticcraft.network;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.network.packets.C2S.*;
import net.kapitencraft.mysticcraft.network.packets.S2C.*;
import net.kapitencraft.mysticcraft.network.packets.SendCompoundTagPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1";

    private static SimpleChannel PACKET_HANDLER;

    private static int messageID = 0;
    private static int id() {
        return messageID++;
    }

    public static <MSG> void sendToServer(MSG message) {
        PACKET_HANDLER.sendToServer(message);
    }

    public static <MSG> void sendToClientPlayer(MSG message, ServerPlayer player) {
        PACKET_HANDLER.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToAllConnectedPlayers(Function<ServerPlayer, MSG> provider, ServerLevel serverLevel) {
        serverLevel.getPlayers(serverPlayer -> true).forEach(serverPlayer -> ModMessages.sendToClientPlayer(provider.apply(serverPlayer), serverPlayer));
    }


    public static void register() {
        PACKET_HANDLER = NetworkRegistry.ChannelBuilder
                .named(MysticcraftMod.res("messages"))
                .networkProtocolVersion(()-> PROTOCOL_VERSION)
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        addSimpleMessage(UseShortBowPacket.class, NetworkDirection.PLAY_TO_SERVER, UseShortBowPacket::new);
        addSimpleMessage(ResetCooldownsPacket.class, NetworkDirection.PLAY_TO_CLIENT, ResetCooldownsPacket::new);
        addSimpleMessage(UpgradeItemPacket.class, NetworkDirection.PLAY_TO_SERVER, UpgradeItemPacket::new);
        addMessage(SendCompoundTagPacket.class, NetworkDirection.PLAY_TO_SERVER, SendCompoundTagPacket::new);
        addMessage(SendCompoundTagPacket.class, NetworkDirection.PLAY_TO_CLIENT, SendCompoundTagPacket::new);
        addMessage(SyncEssenceDataPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncEssenceDataPacket::new);
        addMessage(SyncGemstoneDataToPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncGemstoneDataToPlayerPacket::new);
        addMessage(SyncElytraDataToPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncElytraDataToPlayerPacket::new);
        addMessage(SyncManaDistributionNetworksPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncManaDistributionNetworksPacket::new);
        addMessage(ReforgeItemPacket.class, NetworkDirection.PLAY_TO_SERVER, ReforgeItemPacket::new);
        addMessage(SwingPacket.class, NetworkDirection.PLAY_TO_CLIENT, SwingPacket::new);
        addMessage(UpdatePerksPacket.class, NetworkDirection.PLAY_TO_CLIENT, UpdatePerksPacket::new);
        addMessage(UpgradePerkPacket.class, NetworkDirection.PLAY_TO_SERVER, UpgradePerkPacket::new);
        addMessage(RemoveManaDistributionNetworkElementPacket.class, NetworkDirection.PLAY_TO_CLIENT, RemoveManaDistributionNetworkElementPacket::new);
        addMessage(HammerAbortBreakPacket.class, NetworkDirection.PLAY_TO_CLIENT, HammerAbortBreakPacket::new);
        addMessage(UpdateClassProgressionPacket.class, NetworkDirection.PLAY_TO_CLIENT, UpdateClassProgressionPacket::new);
        addMessage(SelectSpellSlotPacket.class, NetworkDirection.PLAY_TO_SERVER, SelectSpellSlotPacket::new);
        addMessage(BreathParticlesPacket.class, NetworkDirection.PLAY_TO_CLIENT, BreathParticlesPacket::new);
    }

    private static <T extends SimplePacket> void addMessage(Class<T> tClass, NetworkDirection direction, Function<FriendlyByteBuf, T> decoder) {
        PACKET_HANDLER.messageBuilder(tClass, id(), direction)
                .decoder(decoder)
                .encoder(T::toBytes)
                .consumerMainThread(T::handle)
                .add();
    }

    private static <T extends SimplePacket> void addSimpleMessage(Class<T> packetClass, NetworkDirection direction, Supplier<T> supplier) {
        addMessage(packetClass, direction, buf -> supplier.get());
    }
}