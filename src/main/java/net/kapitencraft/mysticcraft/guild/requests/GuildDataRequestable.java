package net.kapitencraft.mysticcraft.guild.requests;

import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class GuildDataRequestable implements IRequestable<GuildDataRequestable.GuildPlayerData[], UUID[]> {

    @Override
    public void writeToNetwork(GuildPlayerData[] target, FriendlyByteBuf buf) {
        NetworkingHelper.writeArray(buf, target, GuildPlayerData::save);
    }

    @Override
    public GuildPlayerData[] getFromNetwork(FriendlyByteBuf buf) {
        return NetworkingHelper.readArray(buf, GuildPlayerData[]::new, GuildPlayerData::read);
    }

    @Override
    public void writeRequest(UUID[] target, FriendlyByteBuf buf) {
        NetworkingHelper.writeArray(buf, target, FriendlyByteBuf::writeUUID);
    }

    @Override
    public UUID[] readRequest(FriendlyByteBuf buf) {
        return NetworkingHelper.readArray(buf, UUID[]::new, FriendlyByteBuf::readUUID);
    }

    @Override
    public GuildPlayerData[] pack(UUID[] source, ServerPlayer player) {
        GameProfileCache cache = player.getLevel().getServer().getProfileCache();
        return Arrays.stream(source)
                .map(cache::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(gameProfile -> new PlayerInfo(gameProfile, true))
                .map(GuildPlayerData::new).toArray(GuildPlayerData[]::new);
    }

    public static class GuildPlayerData {
        private final ResourceLocation skin;
        private final String name;

        public GuildPlayerData(PlayerInfo info) {
            this(info.getSkinLocation(), info.getProfile().getName());
        }

        public GuildPlayerData(ResourceLocation skin, String name) {
            this.skin = skin;
            this.name = name;
        }

        static GuildPlayerData read(FriendlyByteBuf buf) {
            return new GuildPlayerData(new ResourceLocation(buf.readUtf()), buf.readUtf());
        }

        static void save(FriendlyByteBuf buf, GuildPlayerData data) {
            buf.writeUtf(data.skin.toString());
            buf.writeUtf(data.name);
        }

        public String getName() {
            return name;
        }

        public ResourceLocation getSkin() {
            return skin;
        }
    }

    public static class PlayerDataRequest {
        private final UUID[] players;
        private final String guildName;

        public PlayerDataRequest(UUID[] players, String guildName) {
            this.players = players;
            this.guildName = guildName;
        }

        public static PlayerDataRequest read(FriendlyByteBuf buf) {
            return new PlayerDataRequest(NetworkingHelper.readArray(buf, UUID[]::new, FriendlyByteBuf::readUUID), buf.readUtf());
        }

        public void write(FriendlyByteBuf buf) {
            NetworkingHelper.writeArray(buf, players, FriendlyByteBuf::writeUUID);
            buf.writeUtf(guildName);
        }
    }
}
