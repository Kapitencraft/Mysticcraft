package net.kapitencraft.mysticcraft.item.loot_table.modifiers;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.loot_table.IConditional;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SmeltModifier extends ModLootModifier implements IConditional {
    public static final Codec<SmeltModifier> CODEC = LootTableHelper.simpleCodec(SmeltModifier::new);
    private LootContext context = null;
    protected SmeltModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LivingEntity source = LootTableHelper.getLivingSource(context);
        if (source != null && source.getMainHandItem().getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get()) > 0) {
            this.context = context;
            generatedLoot = new ObjectArrayList<>(generatedLoot.stream().map(this::run).toList());
            this.context = null;
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    private ItemStack run(ItemStack unSmelt) {
        if (!unSmelt.isEmpty()) {
            Optional<SmeltingRecipe> optional = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(unSmelt), context.getLevel());
            if (optional.isPresent()) {
                ItemStack itemstack = optional.get().getResultItem();
                if (!itemstack.isEmpty()) {
                    ItemStack itemstack1 = itemstack.copy();
                    itemstack1.setCount(unSmelt.getCount() * itemstack.getCount()); //Forge: Support smelting returning multiple
                    return itemstack1;
                }
            }
        }
        MysticcraftMod.LOGGER.warn(Markers.MOD_MARKER, "Couldn't smelt {} because there is no smelting recipe", unSmelt);
        return unSmelt;
    }

}
