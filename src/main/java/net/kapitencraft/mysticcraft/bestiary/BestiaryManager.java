package net.kapitencraft.mysticcraft.bestiary;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BestiaryManager extends SimpleJsonResourceReloadListener {
    public Map<EntityType<?>, Bestiary> bestiariesForType = new HashMap<>();
    private static final Gson GSON = (new GsonBuilder()).create();

    public BestiaryManager() {
        super(GSON, "bestiary");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller filler) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        map.forEach((location, element) -> {
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(location);
            try {
                DataResult<Bestiary> bestiaryDataResult = Bestiary.CODEC.parse(JsonOps.INSTANCE, element);
                bestiaryDataResult.result().ifPresent(bestiary -> bestiariesForType.put(type, bestiary));
            } catch (Exception e) {
                MysticcraftMod.LOGGER.warn(Markers.BESTIARY_MANAGER, "Unable to load bestiary for '{}': {}", location.toString(), e.getMessage());
            }
        });
        stopwatch.stop();
        MysticcraftMod.LOGGER.info(Markers.BESTIARY_MANAGER, "Loading {} bestiaries took {} ms", bestiariesForType.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}
