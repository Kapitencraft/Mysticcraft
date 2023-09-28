package net.kapitencraft.mysticcraft.networking;

import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgingPacket;
import net.kapitencraft.mysticcraft.networking.packets.S2C.DamageIndicatorPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.kapitencraft.mysticcraft.MysticcraftMod.MOD_ID;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1";

    private static SimpleChannel PACKET_HANDLER;

    private static int messageID = 0;
    private static int id() {
        return messageID++;
    }

    public static <MSG> void addNetworkMessage(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    public static <MSG> void sendToServer(MSG message) {
        PACKET_HANDLER.sendToServer(message);
    }


    public static <MSG> void sendToServer(MSG message, ServerPlayer player) {
        PACKET_HANDLER.send(PacketDistributor.PLAYER.with(()-> player), message);
    }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MOD_ID, "messages"))
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
        net.messageBuilder(DamageIndicatorPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DamageIndicatorPacket::new)
                .encoder(DamageIndicatorPacket::toBytes)
                .consumerMainThread(DamageIndicatorPacket::handle)
                .add();
    }
}