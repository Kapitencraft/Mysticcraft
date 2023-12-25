package net.kapitencraft.mysticcraft.item.loot_table.modifiers;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.loot_table.IConditional;
import net.kapitencraft.mysticcraft.item.loot_table.LootContextReader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

public class OreModifier extends ModLootModifier implements IConditional {
    public static final Codec<OreModifier> CODEC = LootTableHelper.simpleCodec(OreModifier::new);

    protected OreModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        double attributeValue = AttributeHelper.getSaveAttributeValue(ModAttributes.MINING_FORTUNE.get(), LootTableHelper.getLivingSource(context));
        LootContextReader.simple(context, BlockState.class, LootContextParams.BLOCK_STATE).ifPresent(state -> generatedLoot.forEach(stack -> {
            if (stack.getItem() != state.getBlock().asItem()) {
                MathHelper.mul(stack::getCount, stack::setCount, (int) (1 + attributeValue / 100));
            }
        }));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
