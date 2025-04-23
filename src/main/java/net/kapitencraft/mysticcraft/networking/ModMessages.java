package net.kapitencraft.mysticcraft.networking;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgeItemPacket;
import net.kapitencraft.mysticcraft.networking.packets.C2S.UpgradeItemPacket;
import net.kapitencraft.mysticcraft.networking.packets.C2S.UseShortBowPacket;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.kapitencraft.mysticcraft.networking.packets.S2C.*;
import net.kapitencraft.mysticcraft.networking.packets.SendCompoundTagPacket;
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
        addMessage(DisplayTotemActivationPacket.class, NetworkDirection.PLAY_TO_CLIENT, DisplayTotemActivationPacket::new);
        addMessage(SyncEssenceDataPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncEssenceDataPacket::new);
        addMessage(SyncGemstoneDataToPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncGemstoneDataToPlayerPacket::new);
        addMessage(SyncElytraDataToPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT, SyncElytraDataToPlayerPacket::new);
        addMessage(ReforgeItemPacket.class, NetworkDirection.PLAY_TO_SERVER, ReforgeItemPacket::new);
        addMessage(SwingPacket.class, NetworkDirection.PLAY_TO_CLIENT, SwingPacket::new);
    }

    private static <T extends ModPacket> void addMessage(Class<T> tClass, NetworkDirection direction, Function<FriendlyByteBuf, T> decoder) {
        PACKET_HANDLER.messageBuilder(tClass, id(), direction)
                .decoder(decoder)
                .encoder(T::toBytes)
                .consumerMainThread(T::handle)
                .add();
    }

    private static <T extends ModPacket> void addSimpleMessage(Class<T> packetClass, NetworkDirection direction, Supplier<T> supplier) {
        addMessage(packetClass, direction, buf -> supplier.get());
    }
}