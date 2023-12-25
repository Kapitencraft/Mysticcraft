package net.kapitencraft.mysticcraft.item.loot_table.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.misc.string_converter.converter.TextToIntConverter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EnchantmentAddItemModifier extends AddItemModifier {
    public static final Codec<EnchantmentAddItemModifier> CODEC = RecordCodecBuilder.create(enchantmentAddItemModifierInstance ->
            addItemCodecStart(enchantmentAddItemModifierInstance).and(
                    ForgeRegistries.ENCHANTMENTS.getCodec().fieldOf("enchantment").forGetter(i -> i.enchantment)
            ).and(
                    Codec.STRING.fieldOf("provider").forGetter(i -> i.provider)
            ).apply(enchantmentAddItemModifierInstance, EnchantmentAddItemModifier::new)
    );
    private final Enchantment enchantment;
    private final String provider;
    protected EnchantmentAddItemModifier(LootItemCondition[] conditionsIn, Item item, float chance, int maxAmount, Enchantment enchantment, String provider) {
        super(conditionsIn, item, chance, maxAmount);
        this.enchantment = enchantment;
        this.provider = provider;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack stack = LootTableHelper.getTool(context);
        if (stack != null) {
            int enchLevel = stack.getEnchantmentLevel(enchantment);
            TextToIntConverter converter = new TextToIntConverter(Map.of("ench", ()-> enchLevel));
            chance *= converter.transfer(provider);
        }
        return super.doApply(generatedLoot, context);
    }
}
