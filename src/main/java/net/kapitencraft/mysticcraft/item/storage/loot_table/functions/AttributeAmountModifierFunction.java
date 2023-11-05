package net.kapitencraft.mysticcraft.item.storage.loot_table.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.init.ModLootItemFunctions;
import net.kapitencraft.mysticcraft.item.storage.loot_table.IConditional;
import net.kapitencraft.mysticcraft.item.storage.loot_table.modifiers.ModLootModifier;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.BinaryProvider;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class AttributeAmountModifierFunction extends LootItemConditionalFunction implements IConditional {
    private static final Codec<AttributeAmountModifierFunction> CODEC = RecordCodecBuilder.create(attributeAmountModifierFunctionInstance ->
            attributeAmountModifierFunctionInstance.group(
                    ModLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(i -> i.predicates),
                    ForgeRegistries.ATTRIBUTES.getCodec().fieldOf("attribute").forGetter(i -> i.modifier),
                    Formulas.CODEC.fieldOf("formula").forGetter(i -> i.formula)
            ).apply(attributeAmountModifierFunctionInstance, AttributeAmountModifierFunction::new)
    );

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

    @Override
    public LootItemCondition[] getConditions() {
        return predicates;
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

    public static final TagHelper.CodecSerializer<AttributeAmountModifierFunction> SERIALIZER = TagHelper.createNullDefaultedSerializer(CODEC);
}
