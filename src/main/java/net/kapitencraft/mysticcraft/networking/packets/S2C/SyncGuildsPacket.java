package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.guild.GuildUpgradeInstance;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncGuildsPacket implements ModPacket {
    private static ClientLevel clientLevel;
    private final Type type;
    private final CompoundTag tag;

    public SyncGuildsPacket(Type type, CompoundTag tag) {
        this.type = type;
        this.tag = tag;
    }

    public SyncGuildsPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(Type.class), TagHelper.fromString(buf.readUtf()));
    }

    public static SyncGuildsPacket loadAll(Collection<Guild> guilds) {
        ListTag savedGuilds = new ListTag();
        savedGuilds.addAll(guilds.stream().map(Guild::saveToTag).toList());
        return new SyncGuildsPacket(Type.LOAD_ALL, TagHelper.TagBuilder.create()
                .withArg("guilds", savedGuilds, CompoundTag::put)
                .build()
        );
    }
    public static SyncGuildsPacket addGuild(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_GUILD, TagHelper.TagBuilder.create()
                .withArg("owner", player.getUUID(), CompoundTag::putUUID)
                .withArg("name", guild.getName(), CompoundTag::putString)
                .build()
        );
    }
    public static SyncGuildsPacket addPlayer(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_PLAYER, TagHelper.TagBuilder.create()
                .withArg("player", player.getUUID(), CompoundTag::putUUID)
                .withArg("name", guild.getName(), CompoundTag::putString)
                .build()
        );
    }
    public static SyncGuildsPacket removeGuild(Guild guild) {
        return new SyncGuildsPacket(Type.REMOVE_GUILD, TagHelper.TagBuilder.create()
                .withArg("name", guild.getName(), CompoundTag::putString)
                .build()
        );
    }
    public static SyncGuildsPacket changeRank(Player player, Guild.GuildRank rank) {
        return new SyncGuildsPacket(Type.CHANGE_RANK, TagHelper.TagBuilder.create()
                .withArg("player", player.getUUID(), CompoundTag::putUUID)
                .withArg("rank", rank.getRegistryName(), CompoundTag::putString)
                .build()
        );
    }
    public static SyncGuildsPacket leaveGuild(Player player) {
        return new SyncGuildsPacket(Type.LEAVE_GUILD, TagHelper.TagBuilder.create()
                .withArg("player", player.getUUID(), CompoundTag::putUUID)
                .build()
        );
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeUtf(TagHelper.fromCompoundTag(tag));
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
                        MysticcraftMod.sendInfo("received data for {} guilds", Guild.GUILD_MARKER, list.size());
                        GuildHandler.ensureInstanceNotNull();
                        list.forEach(GuildHandler::addNewGuild);
                    }
                    case ADD_GUILD -> {
                        Player player = getPlayer(tag.getUUID("owner"));
                        String name = tag.getString("name");
                        MysticcraftMod.sendInfo("received data for adding new guild '{}' from {}", Guild.GUILD_MARKER, name, player.getName());
                        GuildHandler.getInstance().addNewGuild(name, player);
                    }
                    case ADD_PLAYER -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        String guildName = tag.getString("name");
                        MysticcraftMod.sendInfo("received data for adding {} to '{}'", Guild.GUILD_MARKER, player.getName(), guildName);
                        GuildHandler.getInstance().getGuild(guildName).addMember(player);
                    }
                    case REMOVE_GUILD -> {
                        String guildName = tag.getString("name");
                        MysticcraftMod.sendInfo("received data to remove '{}'", Guild.GUILD_MARKER, guildName);
                        GuildHandler.getInstance().removeGuild(guildName);
                    }
                    case CHANGE_RANK -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        Guild.GuildRank rank = Guild.GuildRank.getByName(tag.getString("rank"));
                        MysticcraftMod.sendInfo("received data to change rank of {} to {}", Guild.GUILD_MARKER, player.getName(), rank);
                        GuildHandler handler = GuildHandler.getInstance();
                        handler.getGuildForPlayer(player).setRank(player.getUUID(), rank);
                    }
                    case LEAVE_GUILD -> {
                        Player player = getPlayer(tag.getUUID("player"));
                        MysticcraftMod.sendInfo("received data to remove {} from their guild", Guild.GUILD_MARKER, player);
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
                .map(SyncGuildsPacket::getFromTag)
                .toList();
    }

    private static CompoundTag fromTag(Tag tag) {
        try {
            return (CompoundTag) tag;
        } catch (ClassCastException e) {
            return new CompoundTag();
        }
    }

    private static Guild getFromTag(CompoundTag tag) {
        int i = 0;
        Guild guild = new Guild(tag.getString("name"), tag.getUUID("owner"), ItemStack.of(tag.getCompound("banner")), GuildUpgradeInstance.load(tag.getCompound("upgrades")));
        while (tag.contains("Player" + i, 10)) {
            CompoundTag tag1 = tag.getCompound("Player" + i);
            Player player = getPlayer(tag1.getUUID("name"));
            guild.addMember(player);
            guild.setRank(player.getUUID(), Guild.GuildRank.getByName(tag1.getString("rank")));
        }
        if (tag.getInt("size") == i) return guild;
        throw new RuntimeException("loaded tag without real guild");
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
