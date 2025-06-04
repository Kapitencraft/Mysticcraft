package net.kapitencraft.mysticcraft.item.capability.reforging;

import com.google.common.base.Stopwatch;
import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.custom.ExtraCodecs;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReforgeManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public ReforgeManager() {
        super(GSON, "reforges");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller filler) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            String[] data = entry.getKey().getPath().split("/");
            if (data.length < 1)
                throw new IllegalStateException("found reforge without name");
            if (data.length < 2) throw new IllegalStateException("found reforge without type declaration");
            String name = data[1];
            String type = data[0];
            try {
                JsonObject object = entry.getValue().getAsJsonObject();
                Reforge.Builder reforge = new Reforge.Builder(name);
                reforge.reforgeType(Reforge.Type.byName(type));

                if (object.has("bonus")) {
                    DataResult<Bonus<?>> result = ExtraCodecs.BONUS.parse(JsonOps.INSTANCE, object.get("bonus"));
                    Bonus<?> bonus = result.getOrThrow(false, (s) -> {});
                    reforge.withBonus(bonus);
                }
                //if (bonus != ModReforgingBonuses.EMPTY.get() && bonus != null) reforge.withBonus(bonus);
                JsonObject mods = object.getAsJsonObject("mods");
                for (Map.Entry<String, JsonElement> modsEntry : mods.entrySet()) {
                    Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(modsEntry.getKey()));
                    JsonArray array = modsEntry.getValue().getAsJsonArray();
                    ReforgeStat stat = new ReforgeStat.Builder(array.asList().stream().map(JsonElement::getAsJsonPrimitive).map(JsonPrimitive::getAsDouble).toList()).build();
                    reforge.addStat(attribute, stat);
                }
                Reforges.registerReforge(reforge.build());
            } catch (Exception e) {
                MysticcraftMod.LOGGER.warn(Markers.REFORGE_MANAGER, "Unable to load reforge '{}': {}", name, e.getMessage());
            }
        }
        stopwatch.stop();
        MysticcraftMod.LOGGER.info(Markers.REFORGE_MANAGER, "loading {} reforges took {} ms", Reforges.getReforgesSize(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
