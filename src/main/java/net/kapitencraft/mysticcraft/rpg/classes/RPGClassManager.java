package net.kapitencraft.mysticcraft.rpg.classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kapitencraft.kap_lib.io.JsonHelper;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RPGClassManager extends SimpleJsonResourceReloadListener {
    private static RPGClassManager instance;

    private final ServerPerksManager perksManager;

    private final Map<ResourceLocation, RPGClass> map = new HashMap<>();


    private final Map<UUID, PlayerClass> classCache = new HashMap<>();

    public static void clearCache() {
        if (instance != null) instance.classCache.clear();
    }

    private RPGClassManager() {
        super(JsonHelper.GSON, "classes");
        this.perksManager = ServerPerksManager.getOrCreateInstance();
    }

    public static RPGClassManager getOrCreateInstance() {
        if (instance == null) instance = new RPGClassManager();
        return instance;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            JsonObject object = entry.getValue().getAsJsonObject();

            PerkTree tree = perksManager.getTree(new ResourceLocation(GsonHelper.getAsString(object, "tree")));
            RPGClass rpgClass = new RPGClass(tree);
            map.put(entry.getKey(), rpgClass);
        }
    }

    public PlayerClass getPlayerClass(ServerPlayer player) {
        UUID uuid = player.getUUID();
        if (classCache.containsKey(uuid)) return classCache.get(uuid);

        PlayerClass playerClass = new PlayerClass(player);
        this.classCache.put(uuid, playerClass);
        return playerClass;
    }
}
