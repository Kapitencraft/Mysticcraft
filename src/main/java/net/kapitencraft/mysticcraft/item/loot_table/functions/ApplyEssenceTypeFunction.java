package net.kapitencraft.mysticcraft.item.loot_table.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.io.serialization.JsonSerializer;
import net.kapitencraft.mysticcraft.item.capability.essence.IEssenceData;
import net.kapitencraft.mysticcraft.item.loot_table.IConditional;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.ModLootModifier;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.registry.ModLootItemFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class ApplyEssenceTypeFunction extends LootItemConditionalFunction implements IConditional {
    private static final Codec<ApplyEssenceTypeFunction> CODEC = RecordCodecBuilder.create(applyEssenceTypeFunctionInstance -> applyEssenceTypeFunctionInstance.group(
            ModLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(i -> i.predicates),
            EssenceType.CODEC.fieldOf("type").forGetter(i -> i.type)
    ).apply(applyEssenceTypeFunctionInstance, ApplyEssenceTypeFunction::new));
    private final EssenceType type;

    protected ApplyEssenceTypeFunction(LootItemCondition[] p_80678_, EssenceType type) {
        super(p_80678_);
        this.type = type;
    }

    @Override
    public LootItemCondition[] getConditions() {
        return predicates;
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        if (stack.getItem() instanceof IEssenceData essenceData) {
            essenceData.saveData(stack, type);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return ModLootItemFunctions.APPLY_ESSENCE_TYPE.get();
    }

    public static final JsonSerializer<ApplyEssenceTypeFunction> SERIALIZER = new JsonSerializer<>(CODEC);
}
