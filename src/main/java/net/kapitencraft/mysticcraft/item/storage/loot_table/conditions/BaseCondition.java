package net.kapitencraft.mysticcraft.item.storage.loot_table.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCondition implements LootItemCondition {


    protected static class ConditionSerializer<T extends BaseCondition> implements Serializer<T> {
        private final Codec<T> codec;
        private final T defaulted;

        protected ConditionSerializer(Codec<T> codec, T defaulted) {
            this.codec = codec;
            this.defaulted = defaulted;
        }

        @Override
        public void serialize(@NotNull JsonObject object, @NotNull T value, @NotNull JsonSerializationContext p_79327_) {
            JsonObject element = (JsonObject) TagHelper.getOrLog(codec.encodeStart(JsonOps.INSTANCE, value), "unable to save condition", new JsonObject());
            element.asMap().forEach(object::add);
        }

        @Override
        public @NotNull T deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext context) {
            return TagHelper.getOrLog(codec.parse(JsonOps.INSTANCE, object), "unable to load condition", defaulted);
        }
    }
}