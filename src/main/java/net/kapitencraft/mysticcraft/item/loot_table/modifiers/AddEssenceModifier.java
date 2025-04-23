package net.kapitencraft.mysticcraft.item.loot_table.modifiers;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.item.loot_table.IConditional;
import net.kapitencraft.mysticcraft.item.material.EssenceItem;
import net.kapitencraft.mysticcraft.item.misc.RNGHelper;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

public class AddEssenceModifier extends ModLootModifier implements IConditional {
    public static final Codec<AddEssenceModifier> CODEC = LootTableHelper.simpleCodec(AddEssenceModifier::new);
    protected AddEssenceModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LivingEntity living = LootTableHelper.getLivingSource(context);
        if (living instanceof Mob mob) {
            if (mob.getMobType() == MobType.UNDEAD) {
                EssenceItem essenceItem = ModItems.ESSENCE.get();
                ItemStack stack = RNGHelper.calculateAndDontDrop(essenceItem, 5, living, 0.0001f);
                essenceItem.saveData(stack, EssenceType.UNDEAD);
                if (stack != ItemStack.EMPTY) {
                    generatedLoot.add(stack);
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}