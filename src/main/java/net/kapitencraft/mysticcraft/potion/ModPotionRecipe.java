package net.kapitencraft.mysticcraft.potion;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.jetbrains.annotations.NotNull;

/**
 * Used in BrewingRecipeRegistry to maintain the mysticcraft behaviour.
 *
 * Most of the code was simply adapted from net.minecraftforge.common.brewing.VanillaBrewingRecipe
 */
public class ModPotionRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack stack)
    {
        Item item = stack.getItem();
        return item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE;
    }


    @Override
    public boolean isIngredient(@NotNull ItemStack stack)
    {
        return ModPotionBrewing.isPotionIngredient(stack);
    }


    @Override
    public @NotNull ItemStack getOutput(ItemStack input, @NotNull ItemStack ingredient)
    {
        if (!input.isEmpty() && !ingredient.isEmpty() && isIngredient(ingredient))
        {
            ItemStack result = ModPotionBrewing.mix(ingredient, input);
            if (result != input)
            {
                return result;
            }
            return ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }
}
