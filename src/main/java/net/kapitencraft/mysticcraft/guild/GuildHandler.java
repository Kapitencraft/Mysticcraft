package net.kapitencraft.mysticcraft.guild;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;

public class GuildHandler extends SavedData {
    private static GuildHandler instance;
    private final HashMap<String, Guild> guilds = new HashMap<>();

    public GuildHandler() {
    }

    public static GuildHandler createDefault() {
        MysticcraftMod.sendInfo("Unable to load Guilds; using default");
        return new GuildHandler();
    }

    public static void setInstance(GuildHandler newInstance) {
        instance = newInstance;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag p_77763_) {
        return saveAllGuilds();
    }

    public static GuildHandler load(CompoundTag tag, MinecraftServer server) {
        MysticcraftMod.sendInfo("loadingData");
        return loadAllGuilds(server, tag);
    }

    public String addNewGuild(String newGuildName, Player owner) {
        if (!guilds.containsKey(newGuildName)) {
            ItemStack stack = owner.getMainHandItem();
            if (stack.getItem() instanceof BannerItem) {
                this.setDirty();
                guilds.put(newGuildName, new Guild(newGuildName, owner, stack, new GuildUpgradeInstance()));
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
            this.guilds.remove(guildName);
            return "success";
        }
    }

    public @Nullable Guild getGuildForBanner(ItemStack banner) {
        if (!(banner.getItem() instanceof BannerItem)) {
            return null;
        }
        for (Guild guild : guilds.values()) {
            if (ItemStack.matches(guild.getBanner(), banner)) {
                return guild;
            }
        }
        return null;
    }

    public Guild getGuild(String name) {
        return guilds.get(name);
    }

    public Guild getGuildForPlayer(Player player) {
        return getGuild(player.getPersistentData().getString("GuildName"));
    }

    private void addGuild(Guild guild) {
        this.guilds.put(guild.getName(), guild);
    }

    public static GuildHandler loadAllGuilds(MinecraftServer server, CompoundTag tag) {
        int i = 0;
        GuildHandler guildHandler = new GuildHandler();
        while (tag.contains("Guild" + i, 10)) {
            Guild guild = Guild.loadFromTag(tag.getCompound("Guild" + i), server);
            MysticcraftMod.sendInfo("Loaded Guild called " + TextHelper.wrapInNameMarkers(guild.getName()));
            guildHandler.addGuild(guild);
            i++;
        }
        return guildHandler;
    }

    public boolean isStackFromGuildBanner(ItemStack stack) {
        for (Guild guild : this.guilds.values()) {
            if (ItemStack.matches(stack, guild.getBanner())) {
                return true;
            }
        }
        return false;
    }

    public CompoundTag saveAllGuilds() {
        CompoundTag save = new CompoundTag();
        int i = 0;
        for (Guild guild : guilds.values()) {
            save.put("Guild" + i, guild.saveToTag());
            for (Player player : guild.getAllMembers()) {
                player.getPersistentData().putString("GuildName", guild.getName());
            }
            i++;
        }
        return save;
    }

    public static GuildHandler getInstance() {
        return instance;
    }
}
