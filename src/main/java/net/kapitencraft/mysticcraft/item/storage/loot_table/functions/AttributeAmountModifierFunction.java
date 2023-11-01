package net.kapitencraft.mysticcraft.item.storage.loot_table.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.init.ModLootItemFunctions;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.BinaryProvider;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class AttributeAmountModifierFunction extends LootItemConditionalFunction {
    private final Attribute modifier;
    private final Formulas formula;
    private AttributeAmountModifierFunction(LootItemCondition[] p_80678_, Attribute attribute, Formulas formula) {
        super(p_80678_);
        this.modifier = attribute;
        this.formula = formula;
    }

    public Attribute getModifier() {
        return modifier;
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, LootContext lootContext) {
        Entity source = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (source instanceof LivingEntity living) {
            double amount = living.getAttributeValue(modifier);
            return formula.provide(stack, amount);
        }
        return stack;
    }

    public interface Formula {
        String getName();
        ItemStack provide(ItemStack stack, double d);
    }

    public enum Formulas implements Formula, SaveAbleEnum, StringRepresentable {
        DEFAULT("default", (stack, d) -> stack.copyWithCount((int) (stack.getCount() * (1 + d / 100))));

        public static final Codec<Formulas> CODEC = StringRepresentable.fromEnum(Formulas::values);

        private final String name;
        private final BinaryProvider<ItemStack, ItemStack, Double> provider;

        Formulas(String name, BinaryProvider<ItemStack, ItemStack, Double> provider) {
            this.name = name;
            this.provider = provider;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public ItemStack provide(ItemStack stack, double d) {
            return provider.provide(stack, d);
        }

        public static Formulas byName(String name) {
            return SaveAbleEnum.getValue(DEFAULT, name, values());
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return ModLootItemFunctions.ATTRIBUTE_MODIFIER.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<AttributeAmountModifierFunction> {
        @Override
        public void serialize(@NotNull JsonObject object, @NotNull AttributeAmountModifierFunction modifier, @NotNull JsonSerializationContext context) {
            super.serialize(object, modifier, context);
            ResourceLocation location = BuiltInRegistries.ATTRIBUTE.getKey(modifier.modifier);
            object.addProperty("attribute", location == null ? "null" : location.toString());
            object.addProperty("formula", modifier.formula.getName());
        }

        @Override
        public @NotNull AttributeAmountModifierFunction deserialize(JsonObject object, @NotNull JsonDeserializationContext context, LootItemCondition @NotNull [] conditions) {
            String attribute = object.getAsJsonPrimitive("attribute").getAsString();
            return new AttributeAmountModifierFunction(conditions, BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(attribute)), Formulas.byName(object.getAsJsonPrimitive("formula").getAsString()));
        }
    }
}
