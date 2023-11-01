package net.kapitencraft.mysticcraft.item.storage.loot_table.modifiers;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.item.misc.RNGDropHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ALL")
public class AddItemModifier extends ModLootModifier {
    protected static <T extends AddItemModifier> Products.P4<RecordCodecBuilder.Mu<T>, LootItemCondition[], Item, Float, Integer> addItemCodecStart(RecordCodecBuilder.Instance<T> instance) {
        return codecStart(instance)
                .and(ForgeRegistries.ITEMS.getCodec()
                        .fieldOf("item").forGetter(m -> m.item)
                ).and(Codec.FLOAT
                        .fieldOf("chance").forGetter(m -> m.chance)
                ).and(Codec.INT
                        .optionalFieldOf("maxAmount", 0).forGetter(m -> m.maxAmount)
                );
    }
    public static final Codec<AddItemModifier> CODEC = RecordCodecBuilder.create(inst -> addItemCodecStart(inst).apply(inst, AddItemModifier::new));
    final Item item;
    final int maxAmount;
    float chance;

    protected AddItemModifier(LootItemCondition[] conditionsIn, Item item, float chance, int maxAmount) {
        super(conditionsIn);
        this.item = item;
        this.chance = chance;
        this.maxAmount = maxAmount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack stack = RNGDropHelper.dontDrop(item, maxAmount, LootTableHelper.getLivingSource(context), chance);
        if (stack != ItemStack.EMPTY) {
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}