package net.kapitencraft.mysticcraft.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.bestiary.Bestiary;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModBestiaryProvider implements DataProvider {
    private final PackOutput output;
    private final LanguageProvider provider;

    private final HashMap<EntityType<?>, BestiaryStore> bestiaryHashMap = new HashMap<>();

    public ModBestiaryProvider(PackOutput output, @Nullable LanguageProvider sup) {
        this.output = output;
        this.provider = sup;
    }

    public void addBestiary(EntityType<?> type, Bestiary bestiary, String... translation) {
        bestiaryHashMap.put(type, new BestiaryStore(bestiary, List.of(translation)));
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        addBestiaries();
        CompletableFuture<?>[] futures = new CompletableFuture[bestiaryHashMap.size()];
        int i = 0;
        for (Map.Entry<EntityType<?>, BestiaryStore> entry : bestiaryHashMap.entrySet()) {
            ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(entry.getKey());
            Path p = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(location.getNamespace()).resolve("bestiary").resolve(location.getPath() + ".json");
            JsonObject object = makeValue(entry.getValue(), location.getPath());
            futures[i++] = DataProvider.saveStable(cachedOutput, object, p);
        }
        return CompletableFuture.allOf(futures);
    }

    private JsonObject makeValue(BestiaryStore bestiaryStore, String entityName) {
        Bestiary bestiary = bestiaryStore.bestiary();
        JsonObject object = new JsonObject();
        object.addProperty("combat_xp", bestiary.getCombatXp());
        object.addProperty("type", bestiary.getType().getName());
        JsonArray array = new JsonArray();
        List<String> translation = bestiaryStore.translations;
        int descLength = translation.size();
        MiscHelper.repeatXTimes(descLength, integer -> {
            String translationId = "best." + entityName + "." + (integer + 1);
            array.add(translationId);
            if (provider != null) provider.add(translationId, translation.get(integer));
        });
        object.add("description", array);
        return object;
    }

    @Override
    public @NotNull String getName() {
        return "Bestiary Provider";
    }

    private record BestiaryStore(Bestiary bestiary, List<String> translations) {
    }


    private void addBestiaries() {
    }

    private static Bestiary makeBestiary(double combatXp, Bestiary.Type type) {
        return new Bestiary(List.of(), combatXp, type);
    }
}