package net.kapitencraft.mysticcraft.item.storage.loot_table.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Function;

public abstract class ModLootModifier extends LootModifier {
    public static <T extends ModLootModifier> Codec<T> simpleCodec(Function<LootItemCondition[], T> function) {
        return RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, function));
    }

    protected ModLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }
}
