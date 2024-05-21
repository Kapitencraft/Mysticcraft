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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        savedGuilds.addAll(guilds.stream().map(Guild::save).toList());
        return new SyncGuildsPacket(Type.LOAD_ALL, builder -> builder
                .withArg("Guilds", savedGuilds, CompoundTag::put)
        );
    }
    public static SyncGuildsPacket addGuild(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_GUILD, builder -> builder
                .withUUID("Owner", player.getUUID())
                .withString("Name", guild.getGuildName())
                .withArg("IsPublic", guild.isPublic(), CompoundTag::putBoolean)
                .withArg("Banner", guild.getBanner(), (tag1, s, stack) -> tag1.put(s, stack.save(new CompoundTag())))
        );
    }
    public static SyncGuildsPacket addPlayer(Player player, Guild guild) {
        return new SyncGuildsPacket(Type.ADD_PLAYER, builder -> builder
                .withUUID("Player", player.getUUID())
                .withString("Name", guild.getGuildName())
        );
    }
    public static SyncGuildsPacket removeGuild(Guild guild) {
        return new SyncGuildsPacket(Type.REMOVE_GUILD, builder -> builder
                .withString("Name", guild.getGuildName())
        );
    }
    public static SyncGuildsPacket changeRank(Player player, Guild.IRank rank) {
        return new SyncGuildsPacket(Type.CHANGE_RANK, builder -> builder
                .withUUID("Player", player.getUUID())
                .withString("Rank", rank.getRegistryName())
        );
    }
    public static SyncGuildsPacket leaveGuild(Player player) {
        return new SyncGuildsPacket(Type.LEAVE_GUILD, builder -> builder
                .withUUID("Player", player.getUUID())
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
                GuildHandler handler = GuildHandler.getClientInstance();
                clientLevel = level;
                switch (type) {
                    case LOAD_ALL -> {
                        List<Guild> list = getAll(tag);
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for {} guilds", list.size());
                        list.forEach(GuildHandler::addGuildClient);
                        clientLevel.players().forEach(player -> GuildHandler.all(clientLevel).forEach(guild -> guild.setOnline(player)));
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "loaded {} Guilds", GuildHandler.all(clientLevel).size());
                    }
                    case ADD_GUILD -> {
                        Player player = getPlayer(tag.getUUID("Owner"));
                        String name = tag.getString("Name");
                        boolean isPublic = tag.getBoolean("IsPublic");
                        ItemStack banner = ItemStack.of(tag.getCompound("Banner"));
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for adding new guild '{}' from {}", name, player.getName());
                        handler.addGuild(name, player, banner, isPublic);
                    }
                    case ADD_PLAYER -> {
                        Player player = getPlayer(tag.getUUID("Player"));
                        String guildName = tag.getString("Name");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data for adding {} to '{}'", player.getName(), guildName);
                        handler.getGuild(guildName).addMember(player);
                    }
                    case REMOVE_GUILD -> {
                        String guildName = tag.getString("Name");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data to remove '{}'", guildName);
                        handler.removeGuild(guildName);
                    }
                    case CHANGE_RANK -> {
                        Player player = getPlayer(tag.getUUID("Player"));
                        String rank = tag.getString("Rank");
                        MysticcraftMod.LOGGER.info(Markers.GUILD, "received data to change rank of {} to {}", player.getName(), rank);
                        handler.getGuildForPlayer(player).setRank(player, rank);
                    }
                    case LEAVE_GUILD -> {
                        Player player = getPlayer(tag.getUUID("Player"));
                        MysticcraftMod.LOGGER.info("received data to remove {} from their guild", player);
                        handler.getGuildForPlayer(player).kickMember(player, Guild.KickStatus.LEAVE);
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
        return IOHelper.readCompoundList(tag, "Guilds")
                .map(Guild::new)
                .toList();
    }

    private static Player getPlayer(UUID uuid) {
        List<AbstractClientPlayer> players = clientLevel.players();
        List<AbstractClientPlayer> list = players.stream().filter(player -> player.getUUID().equals(uuid)).toList();
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new IllegalStateException("found multiple players with same id");
        }
        throw new IllegalStateException("couldn't find player with uuid '" + uuid + "'");
    }
}
