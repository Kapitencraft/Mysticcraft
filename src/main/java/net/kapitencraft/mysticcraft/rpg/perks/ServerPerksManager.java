package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.common.collect.Sets;
import com.google.gson.*;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.misc.ModLevelResources;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class ServerPerksManager extends SimplePreparableReloadListener<ServerPerksManager.Data> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static ServerPerksManager instance;

    private final Map<ResourceLocation, Perk> perks = new HashMap<>();
    private final Set<Perk> roots = Sets.newLinkedHashSet();

    private final Map<ResourceLocation, PerkTree> trees = new HashMap<>();

    private static final Gson GSON = (new GsonBuilder()).create();

    private ServerPerksManager() {
        instance = this;
    }

    @Override
    protected Data prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, JsonElement> perks = new HashMap<>();
        SimpleJsonResourceReloadListener.scanDirectory(pResourceManager, "perks", GSON, perks);
        Map<ResourceLocation, JsonElement> perkTrees = new HashMap<>();
        SimpleJsonResourceReloadListener.scanDirectory(pResourceManager, "perk_trees", GSON, perkTrees);
        return new Data(perks, perkTrees);
    }

    public static ServerPerksManager getOrCreateInstance() {
        if (instance != null) return instance;
        return new ServerPerksManager();
    }

    private final Map<UUID, PlayerPerks> perksCache = new HashMap<>();

    public static void clearCache() {
        getOrCreateInstance().perksCache.clear();
    }

    public static PerkReward readRewards(JsonObject reward) {
        DataResult<? extends PerkReward> result = PerkReward.CODEC.parse(JsonOps.INSTANCE, reward);
        return result.resultOrPartial(s -> LOGGER.warn("unable to parse rewards: {}", s)).orElse(null);
    }

    public static JsonElement saveRewards(PerkReward reward) {
        DataResult<JsonElement> result = PerkReward.CODEC.encodeStart(JsonOps.INSTANCE, reward);
        return result.resultOrPartial(s -> LOGGER.warn("unable encode rewards: {}", s)).orElse(JsonNull.INSTANCE);
    }

    @Override
    protected void apply(Data data, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        this.perks.clear();
        this.roots.clear();

        Map<ResourceLocation, Perk.Builder> perks = new HashMap<>();
        data.perks.forEach((location, jsonElement) -> {
            try {
                JsonObject object = GsonHelper.convertToJsonObject(jsonElement, "perk");
                Perk.Builder builder = Perk.Builder.fromJson(object);
                if (!builder.valid()) return;
                perks.put(location, builder);
            } catch (Exception e) {
                LOGGER.warn("error parsing perk '{}': {}", location, e.getMessage());
            }
        });

        while(!perks.isEmpty()) {
            boolean flag = false;
            Iterator<Map.Entry<ResourceLocation, Perk.Builder>> iterator = perks.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<ResourceLocation, Perk.Builder> entry = iterator.next();
                ResourceLocation resourcelocation = entry.getKey();
                Perk.Builder builder = entry.getValue();
                if (builder.canBuild(this.perks::get)) {
                    Perk perk = builder.build(resourcelocation);
                    this.perks.put(resourcelocation, perk);
                    flag = true;
                    iterator.remove();
                    if (perk.getParent() == null) {
                        this.roots.add(perk);
                    }
                }
            }

            if (!flag) {
                for(Map.Entry<ResourceLocation, Perk.Builder> entry1 : perks.entrySet()) {
                    LOGGER.error("Couldn't load advancement {}: {}", entry1.getKey(), entry1.getValue());
                }
                break;
            }
        }
        this.roots.forEach(TreeNodePosition::run);

        data.trees.forEach((location, jsonElement) -> {
            try {
                JsonObject object = GsonHelper.convertToJsonObject(jsonElement, "perk group");

                PerkTree.Builder builder = PerkTree.Builder.fromJson(object);

                if (builder.canBuild(this::getRoot)) this.trees.put(location, builder.build(location));
                else throw new IllegalStateException("unable to read root");
            } catch (Exception e) {
                LOGGER.warn("unable to read tab group '{}': {}", location, e.getMessage());
            }
        });
    }

    private Perk getRoot(ResourceLocation location) {
        Perk rootPerk = this.perks.get(location);
        if (rootPerk == null || !this.roots.contains(rootPerk)) {
            throw new IllegalStateException("root not found: " + location);
        }
        return rootPerk;
    }

    public Perk getPerk(ResourceLocation key) {
        return this.perks.get(key);
    }

    public PlayerPerks getPerks(ServerPlayer player) {
        MinecraftServer server = player.server;
        UUID uuid = player.getUUID();
        if (perksCache.containsKey(uuid)) return perksCache.get(uuid);

        PlayerPerks playerPerks = new PlayerPerks(server.getFixerUpper(), this, server.getWorldPath(ModLevelResources.PERKS).resolve(uuid + ".json"), player);
        this.perksCache.put(uuid, playerPerks);
        return playerPerks;
    }

    public Set<Perk> getRoots() {
        return this.roots;
    }

    public @Nullable PerkTree getTree(ResourceLocation treeId) {
        return this.trees.get(treeId);
    }

    public CompletableFuture<Suggestions> createTreeSuggestions(SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(this.trees.keySet().stream().map(ResourceLocation::toString), builder);
    }

    public record Data(Map<ResourceLocation, JsonElement> perks, Map<ResourceLocation, JsonElement> trees) {
    }
}
