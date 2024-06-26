package net.kapitencraft.mysticcraft.guild;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.guild.requests.CreateGuildRequestable;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.helpers.Timer;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGuildsPacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
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
            return getClientInstance();
        } else {
            ServerLevel serverLevel = (ServerLevel) level;
            return serverLevel.getDataStorage().computeIfAbsent(GuildHandler::load, GuildHandler::createDefault, "guilds");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static GuildHandler getClientInstance() {
        ensureInstanceNotNull();
        return clientInstance;
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

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        MysticcraftMod.LOGGER.info("saving Guilds...");
        Timer.start();
        tag.put("Guilds", saveAllGuilds());
        MysticcraftMod.LOGGER.info("saving Guilds took {} ms", Timer.getPassedTime());
        return tag;
    }

    public static GuildHandler load(CompoundTag tag) {
        MysticcraftMod.LOGGER.info(Markers.GUILD, "loading Guilds...");
        return loadAllGuilds(tag);
    }

    public SimpleSuccessResult addNewGuild(CreateGuildRequestable.CreateGuildData newGuildData, Player owner) {
        if (guilds.containsKey(newGuildData.name())) {
            return SimpleSuccessResult.fail("duplicateName");
        } else if (isStackFromGuildBanner(newGuildData.banner())) {
            return SimpleSuccessResult.fail("duplicateBanner");
        }
        ItemStack banner = newGuildData.banner();
        if (!banner.is(ItemTags.BANNERS)) {
            MysticcraftMod.LOGGER.warn(Markers.GUILD, "Player '{}' attempted to create guild with non-Banner item {}", owner.getGameProfile().getName(), banner);
            return SimpleSuccessResult.fail("illegalBannerItem");
        }
        this.setDirty();
        Guild guild = new Guild(newGuildData.name(), owner, banner, newGuildData.isPublic());
        guilds.put(newGuildData.name(), guild);
        owner.getPersistentData().putString(Guild.MemberContainer.PLAYER_GUILD_NAME_TAG, newGuildData.name());
        if (owner instanceof ServerPlayer player) {
            ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.addGuild(owner, guild), player.getLevel());
        }
        return SimpleSuccessResult.success("success");
    }

    /**
     * only used for adding Guilds to the client instance, do not use!
     */
    public void addGuild(String name, Player owner, ItemStack banner, boolean isPublic) {
        owner.getPersistentData().putString(Guild.MemberContainer.PLAYER_GUILD_NAME_TAG, name);
        this.guilds.put(name, new Guild(name, owner, banner, isPublic));
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
        clientInstance.reviveGuild(guild);
    }

    private void reviveGuild(Guild guild) {
        if (guild == null) {
            this.setDirty();
            return;
        }
        this.guilds.put(guild.getGuildName(), guild);
    }

    private static GuildHandler loadAllGuilds(CompoundTag tag) {
        GuildHandler guildHandler = new GuildHandler();
        Timer.start();
        Stream<CompoundTag> tags = IOHelper.readCompoundList(tag, "Guilds");
        long count = tags.map(Guild::loadFromTag).peek(guildHandler::reviveGuild).count();
        guildHandler.allGuilds().forEach(guild -> guild.reviveWarOpponents(guildHandler));
        MysticcraftMod.LOGGER.info(Markers.GUILD, "loading {} Guilds took {} ms", count, Timer.getPassedTime());
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

    public void invalidate() {
        this.guilds.clear();
    }
}
