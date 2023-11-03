package net.kapitencraft.mysticcraft.item.storage.loot_table.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class LootTableTypeCondition extends BaseCondition {
    private static final LootTableTypeCondition EMPTY = new LootTableTypeCondition(null);
    private static final Codec<LootTableTypeCondition> CODEC = RecordCodecBuilder.create(lootTableTypeConditionInstance ->
            lootTableTypeConditionInstance.group(
                    TagKeyCondition.Type.CODEC.fieldOf("type").forGetter(i -> i.type)
            ).apply(lootTableTypeConditionInstance, LootTableTypeCondition::new)
    );
    private final TagKeyCondition.Type type;

    public LootTableTypeCondition(TagKeyCondition.Type type) {
        this.type = type;
    }

    @Override
    public LootItemConditionType getType() {
        return null;
    }

    @Override
    public boolean test(LootContext context) {
        return type.is(context);
    }

    public static final TagHelper.CodecSerializer<LootTableTypeCondition> SERIALIZER = TagHelper.createSerializer(CODEC, EMPTY);
}