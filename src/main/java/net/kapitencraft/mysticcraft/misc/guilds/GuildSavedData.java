package net.kapitencraft.mysticcraft.misc.guilds;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class GuildSavedData extends SavedData {
    private final GuildHandler handler;

    public GuildSavedData(GuildHandler handler) {
        this.handler = handler;
    }

    public static GuildSavedData createDefault() {
        return new GuildSavedData(GuildHandler.getInstance());
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag p_77763_) {
        MysticcraftMod.sendWarn("save");
        this.setDirty();
        return handler.saveAllGuilds();
    }

    public static GuildSavedData load(CompoundTag tag, MinecraftServer server) {
        MysticcraftMod.sendInfo("loadingData");
        return new GuildSavedData(GuildHandler.loadAllGuilds(server, tag));
    }
}
