package net.kapitencraft.mysticcraft.guild;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGuildsPacket;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;


@Mod.EventBusSubscriber
public class GuildHandler extends SavedData {
    private static GuildHandler clientInstance;
    private final HashMap<String, Guild> guilds = new HashMap<>();

    public static GuildHandler getInstance(Level level) {
        if (level instanceof ClientLevel) {
            return clientInstance;
        } else {
            ServerLevel serverLevel = (ServerLevel) level;
            MinecraftServer server = serverLevel.getServer();
            return serverLevel.getDataStorage().computeIfAbsent(tag -> GuildHandler.load(tag, server), GuildHandler::createDefault, "guilds");
        }
    }

    public static GuildHandler createDefault() {
        MysticcraftMod.LOGGER.info(Markers.GUILD, "no Guilds found; using default");
        return new GuildHandler();
    }

    public static Collection<Guild> all(Level level) {
        return getInstance(level).allGuilds();
    }

    public Collection<Guild> allGuilds() {
        return guilds.values();
    }

    public static void ensureInstanceNotNull() {
        if (clientInstance == null) clientInstance = new GuildHandler();
    }

    public static void setInstance(GuildHandler newInstance) {
        clientInstance = newInstance;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        MysticcraftMod.LOGGER.info("saving Guilds...");
        long time = Util.getMillis();
        tag.put("Guilds", saveAllGuilds());
        MysticcraftMod.LOGGER.info("saving Guilds took {} ms", Util.getMillis() - time);
        return tag;
    }

    public static GuildHandler load(CompoundTag tag, MinecraftServer server) {
        MysticcraftMod.LOGGER.info(Markers.GUILD, "loading Guilds...");
        return loadAllGuilds(server.getLevel(Level.OVERWORLD), tag);
    }

    public String addNewGuild(String newGuildName, Player owner) {
        if (!guilds.containsKey(newGuildName)) {
            ItemStack stack = owner.getMainHandItem();
            if (stack.getItem() instanceof BannerItem) {
                Guild bannerGuild = getGuildForBanner(stack);
                if (bannerGuild != null) return "duplicateBanner";
                this.setDirty();
                Guild guild = new Guild(newGuildName, owner.getUUID(), stack, new GuildUpgradeInstance());
                guilds.put(newGuildName, guild);
                owner.getPersistentData().putString(Guild.MemberContainer.PLAYER_GUILD_NAME_TAG, newGuildName);
                if (owner instanceof ServerPlayer player) {
                    ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.addGuild(owner, guilds.get(newGuildName)), player.getLevel());
                }
                return "success";
            }
            return "noBanner";
        }
        return "failed";
    }

    public String removeGuild(String guildName) {
        if (!this.guilds.containsKey(guildName)) {
            return "noSuchGuild";
        } else {
            this.setDirty();
            Guild guild = this.guilds.get(guildName);
            guild.disband();
            this.guilds.remove(guildName);
            return "success";
        }
    }

    public @Nullable Guild getGuildForBanner(ItemStack banner) {
        if (!(banner.getItem() instanceof BannerItem)) {
            return null;
        }
        return MapStream.of(allGuilds().stream()
                .collect(CollectorHelper.createMapForKeys(Guild::getBanner)))
                .filterKeys(stack -> ItemStack.matches(stack, banner))
                .toMap()
                .values()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Guild getGuild(String name) {
        return guilds.get(name);
    }

    public Guild getGuildForPlayer(Player player) {
        return getGuild(player.getPersistentData().getString(Guild.MemberContainer.PLAYER_GUILD_NAME_TAG));
    }

    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        all(event.getEntity().level).forEach(guild -> guild.setOnline(event.getEntity()));
    }

    @SubscribeEvent
    public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        all(event.getEntity().level).forEach(guild -> guild.setOffline(event.getEntity()));
    }

    public static void addGuildClient(Guild guild) {
        ensureInstanceNotNull();
        clientInstance.addGuild(guild);
    }

    private void addGuild(Guild guild) {
        if (guild == null) {
            this.setDirty();
            return;
        }
        this.guilds.put(guild.getGuildName(), guild);
    }

    public static GuildHandler loadAllGuilds(Level level, CompoundTag tag) {
        GuildHandler guildHandler = new GuildHandler();
        long j = Util.getMillis();
        Stream<CompoundTag> tags = IOHelper.readCompoundList(tag, "Guilds");
        long count = tags.map(Guild::loadFromTag).peek(guildHandler::addGuild).count();
        guildHandler.allGuilds().forEach(guild -> guild.reviveWarOpponents(guildHandler));
        MysticcraftMod.LOGGER.info(Markers.GUILD, "loading {} Guilds took {} ms", count, Util.getMillis() - j);
        return guildHandler;
    }

    public boolean isStackFromGuildBanner(ItemStack stack) {
        return this.allGuilds()
                .stream()
                .map(Guild::getBanner)
                .anyMatch(stack1 -> ItemStack.matches(stack1, stack));
    }

    public ListTag saveAllGuilds() {
        ListTag listTag = new ListTag();
        listTag.addAll(allGuilds().stream().map(Guild::save).toList());
        this.guilds.clear();
        return listTag;
    }

    @OnlyIn(Dist.CLIENT)
    public static GuildHandler getInstance() {
        ensureInstanceNotNull();
        return clientInstance;
    }
}
