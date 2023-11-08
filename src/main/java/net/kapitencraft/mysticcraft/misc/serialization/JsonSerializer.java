package net.kapitencraft.mysticcraft.misc.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import org.jetbrains.annotations.NotNull;

public class JsonSerializer<T> extends Serializer<JsonElement, JsonOps, T> implements net.minecraft.world.level.storage.loot.Serializer<T> {
    public JsonSerializer(Codec<T> codec, T defaulted) {
        super(JsonOps.INSTANCE, codec, defaulted);
    }

    public JsonSerializer(Codec<T> codec) {
        this(codec, null);
    }

    @Override
    JsonObject getSerializeDefault() {
        return new JsonObject();
    }

    @Override
    public void serialize(@NotNull JsonObject jsonObject, @NotNull T t, @NotNull JsonSerializationContext context) {
        JsonObject object = (JsonObject) serialize(t);
        object.asMap().forEach(jsonObject::add);
    }

    @Override
    public @NotNull T deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext context) {
        return deserialize(object);
    }
}
