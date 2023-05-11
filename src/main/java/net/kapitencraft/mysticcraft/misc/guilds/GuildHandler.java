package net.kapitencraft.mysticcraft.misc.guilds;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

public class GuildHandler {
    private final MinecraftServer server;
    private static GuildHandler instance;

    private final HashMap<String, Guild> guilds = new HashMap<>();

    public GuildHandler(MinecraftServer server) {
        if (instance == null) {
            this.server = server;
            instance = this;
        } else {
            throw new IllegalStateException("can not create Guild-Hander with already existing");
        }
    }

    public String addNewGuild(String newGuildName, Player owner) {
        if (!guilds.containsKey(newGuildName)) {
            ItemStack stack = owner.getMainHandItem();
            if (stack.getItem() instanceof BannerItem) {
                guilds.put(newGuildName, new Guild(newGuildName, owner, stack));
                return "success";
            }
            return "noBanner";
        }
        return "failed";
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

    private void addGuild(Guild guild) {
        this.guilds.put(guild.getName(), guild);
    }

    public static GuildHandler loadAllGuilds(MinecraftServer server, CompoundTag tag) {
        int i = 0;
        GuildHandler guildHandler = new GuildHandler(server);
        while (tag.contains("Guild" + i, 10)) {
            guildHandler.addGuild(Guild.loadFromTag(tag.getCompound("Guild" + i), server));
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
            i++;
        }
        return save;
    }

    public static GuildHandler getInstance() {
        return instance;
    }
}