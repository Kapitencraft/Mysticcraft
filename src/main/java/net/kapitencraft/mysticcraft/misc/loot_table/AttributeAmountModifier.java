package net.kapitencraft.mysticcraft.misc.loot_table;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.BinaryProvider;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

public class AttributeAmountModifier extends LootItemConditionalFunction {
    private final Attribute modifier;
    private final Formula formula;
    protected AttributeAmountModifier(LootItemCondition[] p_80678_, Attribute attribute, Formula formula) {
        super(p_80678_);
        this.modifier = attribute;
        this.formula = formula;
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

    public enum Formulas implements Formula, SaveAbleEnum {
        DEFAULT("default", (value1, value2) -> value1);
        ;

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

        public static Formula byName(String name) {
            return SaveAbleEnum.getValue(DEFAULT, name, values());
        }
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return ModLootItemFunctions.ATTRIBUTE_MODIFIER;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<AttributeAmountModifier> {
        @Override
        public void serialize(@NotNull JsonObject object, @NotNull AttributeAmountModifier modifier, JsonSerializationContext context) {
            super.serialize(object, modifier, context);
            ResourceLocation location = BuiltInRegistries.ATTRIBUTE.getKey(modifier.modifier);
            object.addProperty("attribute", location == null ? "null" : location.toString());
            object.addProperty("formula", modifier.formula.getName());
        }

        @Override
        public AttributeAmountModifier deserialize(JsonObject object, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new AttributeAmountModifier(conditions, BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(object.getAsJsonPrimitive("attribute").getAsString())), Formulas.byName(object.getAsJsonPrimitive("formula").getAsString()));
        }
    }
}
