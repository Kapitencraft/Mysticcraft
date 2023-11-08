package net.kapitencraft.mysticcraft.misc.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.minecraft.world.level.storage.loot.Serializer;
import org.jetbrains.annotations.NotNull;

public record CodecSerializer<T>(Codec<T> codec, T defaulted) implements Serializer<T> {
    @Override
    public void serialize(@NotNull JsonObject object, @NotNull T value, @NotNull JsonSerializationContext p_79327_) {
        JsonObject element = (JsonObject) TagHelper.getOrLog(codec.encodeStart(JsonOps.INSTANCE, value), new JsonObject());
        element.asMap().forEach(object::add);
    }

    @Override
    public @NotNull T deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext context) {
        return TagHelper.getOrLog(codec.parse(JsonOps.INSTANCE, object), defaulted);
    }
}