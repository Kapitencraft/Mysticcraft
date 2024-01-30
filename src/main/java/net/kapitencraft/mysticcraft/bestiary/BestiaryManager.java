package net.kapitencraft.mysticcraft.bestiary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.Timer;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestiaryManager extends SimpleJsonResourceReloadListener {
    public Map<EntityType<?>, Bestiary> bestiariesForType = new HashMap<>();
    private static final Gson GSON = (new GsonBuilder()).create();

    public BestiaryManager() {
        super(GSON, "bestiary");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller filler) {
        Timer.start();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entry.getKey());
            JsonElement element = entry.getValue();
            try {
                JsonObject object = element.getAsJsonObject();
                List<String> description = object.getAsJsonArray("description").asList().stream().map(JsonElement::getAsString).toList();
                double combatXp = object.getAsJsonPrimitive("combat_xp").getAsDouble();
                String s = object.getAsJsonPrimitive("type").getAsString();
                bestiariesForType.put(type, new Bestiary(description, combatXp, Bestiary.Type.getByName(s)));
            } catch (Exception e) {
                MysticcraftMod.LOGGER.warn(Markers.BESTIARY_MANAGER, "Unable to load bestiary for '{}': {}", entry.getKey().toString(), e.getMessage());
            }
        }
        MysticcraftMod.LOGGER.info(Markers.BESTIARY_MANAGER, "Loading {} bestiaries took {} ms", bestiariesForType.size(), Timer.getPassedTime());
    }

}
