package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class SyncGuildsPacket implements ModPacket {
    private static ClientLevel clientLevel;
    private final Type type;
    private final CompoundTag tag;

    public SyncGuildsPacket(Type type, UnaryOperator<IOHelper.TagBuilder> tagCreator) {
        this.type = type;
        IOHelper.TagBuilder builder = IOHelper.TagBuilder.create();
        this.tag = tagCreator.apply(builder).build();
    }

    public SyncGuildsPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(Type.class);
        this.tag = IOHelper.fromString(buf.readUtf());
    }

    public static SyncGuildsPacket loadAll(Collection<Guild> guilds) {
        ListTag savedGuilds = new ListTag();
        savedGuilds.addAll(guilds.stream().map(Guild::saveWithPlayers).toList());
        return new SyncGuildsPacket(Type.LOAD_ALL, builder -> builder
                .withArg("guilds", savedGuilds, CompoundTag::put)
        );
    }
    public static SyncGuildsPacket addGuild(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_GUILD, builder -> builder
                .withUUID("owner", player.getUUID())
                .withString("name", guild.getName())
        );
    }
    public static SyncGuildsPacket addPlayer(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_PLAYER, builder -> builder
                .withUUID("player", player.getUUID())
                .withString("name", guild.getName())
        );
    }
    public static SyncGuildsPacket removeGuild(Guild guild) {
        return new SyncGuildsPacket(Type.REMOVE_GUILD, builder -> builder
                .withString("name", guild.getName())
        );
    }
    public static SyncGuildsPacket changeRank(Player player, Guild.GuildRank rank) {
        return new SyncGuildsPacket(Type.CHANGE_RANK, builder -> builder
                .withUUID("player", player.getUUID())
                .withString("rank", rank.getRegistryName())
        );
    }
    public static SyncGuildsPacket leaveGuild(Player player) {
        return new SyncGuildsPacket(Type.LEAVE_GUILD, builder -> builder
                .withUUID("player", player.getUUID())
        );
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeUtf(IOHelper.fromCompoundTag(tag));
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null && Minecraft.getInstance().getCameraEntity() != null) {
                clientLevel = level;
                switch (type) {
                    case LOAD_ALL -> {
                        List<Guild> list = getAll(tag);
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for {} guilds", list.size());
                        list.forEach(GuildHandler::addNewGuild);
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "loaded {} Guilds", GuildHandler.all().size());
                    }
                    case ADD_GUILD -> {
                        Player player = getPlayer(tag.getUUID("owner"));
                        String name = tag.getString("name");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for adding new guild '{}' from {}", name, player.getName());
                        GuildHandler.getInstance().addNewGuild(name, player);
                    }
                    case ADD_PLAYER -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        String guildName = tag.getString("name");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for adding {} to '{}'", player.getName(), guildName);
                        GuildHandler.getInstance().getGuild(guildName).addMember(player);
                    }
                    case REMOVE_GUILD -> {
                        String guildName = tag.getString("name");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data to remove '{}'", guildName);
                        GuildHandler.getInstance().removeGuild(guildName);
                    }
                    case CHANGE_RANK -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        Guild.GuildRank rank = Guild.GuildRank.getByName(tag.getString("rank"));
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data to change rank of {} to {}", player.getName(), rank);
                        GuildHandler handler = GuildHandler.getInstance();
                        handler.getGuildForPlayer(player).setRank(player, rank);
                    }
                    case LEAVE_GUILD -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        MysticcraftMod.LOGGER.info("received data to remove {} from their guild", player);
                        GuildHandler.getInstance().getGuildForPlayer(player).kickMember(player);
                    }
                }
            }
        });
        return true;
    }

    public enum Type {
        LOAD_ALL,
        ADD_PLAYER,
        ADD_GUILD,
        REMOVE_GUILD,
        CHANGE_RANK,
        LEAVE_GUILD
    }

    private List<Guild> getAll(CompoundTag tag) {
        return tag.getList("guilds", Tag.TAG_COMPOUND).stream()
                .map(SyncGuildsPacket::fromTag)
                .map(Guild::new)
                .toList();
    }

    private static CompoundTag fromTag(Tag tag) {
        try {
            return (CompoundTag) tag;
        } catch (ClassCastException e) {
            return new CompoundTag();
        }
    }

    private static Player getPlayer(UUID uuid) {
        List<AbstractClientPlayer> players = clientLevel.players();
        List<AbstractClientPlayer> stream = players.stream().filter(player -> player.getUUID().equals(uuid)).toList();
        if (stream.size() == 1) {
            return stream.get(0);
        } else if (stream.size() > 1) {
            throw new IllegalStateException("found multiple players with same id");
        }
        throw new IllegalStateException("couldn't find player with uuid '" + uuid + "'");
    }
}
