package net.kapitencraft.mysticcraft.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.item.storage.loot_table.LootContextReader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifierManager;

@SuppressWarnings("ALL")
public class LootTableHelper {

    public static final Codec<LootItemCondition[]> CONDITION_CODEC = Codec.PASSTHROUGH.flatXmap(
            d ->
            {
                try
                {
                    LootItemCondition[] conditions = LootModifierManager.GSON_INSTANCE.fromJson(getJson(d), LootItemCondition[].class);
                    return DataResult.success(conditions);
                }
                catch (JsonSyntaxException e)
                {
                    LootModifierManager.LOGGER.warn("Unable to decode loot conditions", e);
                    return DataResult.error(e.getMessage());
                }
            },
            conditions ->
            {
                try
                {
                    JsonElement element = LootModifierManager.GSON_INSTANCE.toJsonTree(conditions);
                    return DataResult.success(new Dynamic<>(JsonOps.INSTANCE, element));
                }
                catch (JsonSyntaxException e)
                {
                    LootModifierManager.LOGGER.warn("Unable to encode loot conditions", e);
                    return DataResult.error(e.getMessage());
                }
            }
    );

    static <U> JsonElement getJson(Dynamic<?> dynamic) {
        Dynamic<U> typed = (Dynamic<U>) dynamic;
        return typed.getValue() instanceof JsonElement ?
                (JsonElement) typed.getValue() :
                typed.getOps().convertTo(JsonOps.INSTANCE, typed.getValue());
    }


    public static Player getPlayerSource(LootContext context) {
        return getEntitySource(context) instanceof Player player ? player : null;
    }

    public static LivingEntity getLivingSource(LootContext context) {
        return getEntitySource(context) instanceof LivingEntity living ? living : null;
    }

    public static ItemStack getTool(LootContext context) {
        return (getLivingSource(context) == null) ? ItemStack.EMPTY : getLivingSource(context).getMainHandItem();
    }

    public static Entity getEntitySource(LootContext context) {
        return LootContextReader.of(context, Entity.class).withParam(LootContextParams.KILLER_ENTITY).ifNull(LootContextParams.THIS_ENTITY).getValue();
    }
}