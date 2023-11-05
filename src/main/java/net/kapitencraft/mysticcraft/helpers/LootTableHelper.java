package net.kapitencraft.mysticcraft.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.item.storage.loot_table.IConditional;
import net.kapitencraft.mysticcraft.item.storage.loot_table.LootContextReader;
import net.kapitencraft.mysticcraft.item.storage.loot_table.modifiers.ModLootModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.function.Function;

@SuppressWarnings("ALL")
public class LootTableHelper {
    public static <T extends IConditional> Codec<T> simpleCodec(Function<LootItemCondition[], T> function) {
        return RecordCodecBuilder.create(instance -> instance.group(
                ModLootModifier.LOOT_CONDITIONS_CODEC.optionalFieldOf("conditions", new LootItemCondition[0]).forGetter(IConditional::getConditions)
        ).apply(instance, function));
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