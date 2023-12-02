package net.kapitencraft.mysticcraft.networking;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgingPacket;
import net.kapitencraft.mysticcraft.networking.packets.C2S.UpgradeItemPacket;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGuildsPacket;
import net.kapitencraft.mysticcraft.networking.packets.SendCompoundTagPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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


    public static <MSG> void sendToServer(MSG message, ServerPlayer player) {
        PACKET_HANDLER.send(PacketDistributor.PLAYER.with(()-> player), message);
    }

    public static <MSG> void sendToClientPlayer(MSG message, ServerPlayer player) {
        PACKET_HANDLER.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToAllConnectedPlayers(Provider<MSG, ServerPlayer> provider, ServerLevel serverLevel) {
        serverLevel.getPlayers(serverPlayer -> true).forEach(serverPlayer -> ModMessages.sendToClientPlayer(provider.provide(serverPlayer), serverPlayer));
    }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(MysticcraftMod.res("messages"))
                .networkProtocolVersion(()-> PROTOCOL_VERSION)
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        PACKET_HANDLER = net;
        net.messageBuilder(ReforgingPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReforgingPacket::new)
                .encoder(ReforgingPacket::toBytes)
                .consumerMainThread(ReforgingPacket::handle)
                .add();
        net.messageBuilder(UpgradeItemPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpgradeItemPacket::new)
                .encoder(UpgradeItemPacket::toBytes)
                .consumerMainThread(UpgradeItemPacket::handle)
                .add();
        net.messageBuilder(SendCompoundTagPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SendCompoundTagPacket::new)
                .encoder(SendCompoundTagPacket::toBytes)
                .consumerMainThread(SendCompoundTagPacket::handle)
                .add();
        net.messageBuilder(SendCompoundTagPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SendCompoundTagPacket::new)
                .encoder(SendCompoundTagPacket::toBytes)
                .consumerMainThread(SendCompoundTagPacket::handle)
                .add();
        net.messageBuilder(SyncGuildsPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncGuildsPacket::new)
                .encoder(SyncGuildsPacket::toBytes)
                .consumerMainThread(SyncGuildsPacket::handle)
                .add();
    }
}