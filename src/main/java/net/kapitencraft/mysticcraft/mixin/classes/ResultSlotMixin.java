package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(ResultSlot.class)
public abstract class ResultSlotMixin extends Slot {
    public ResultSlotMixin(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        Optional<CraftingRecipe> recipe = player.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, this.getCraftSlots(), player.level);
        NonNullList<ItemStack> itemsFor = player.level.getRecipeManager().getRemainingItemsFor(RecipeType.CRAFTING, this.getCraftSlots(), player.level);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
        for(int i = 0; i < itemsFor.size(); ++i) {
            ItemStack itemstack = this.getCraftSlots().getItem(i);
            ItemStack itemstack1 = itemsFor.get(i);
            if (!itemstack.isEmpty() && recipe.isPresent()) {
                CraftingRecipe recipe1 = recipe.get();
                int a = 1;
                if (recipe1 instanceof ShapedRecipe || recipe1 instanceof ShapelessRecipe) {
                    Item item = itemstack.getItem();
                    NonNullList<Ingredient> ingredients = recipe1.getIngredients();
                    for (Ingredient ingredient : ingredients) {
                        for (ItemStack stack1 : ingredient.getItems()) {
                            if (stack1.is(item)) {
                                a = stack1.getCount();
                            }
                        }
                    }
                }
                this.getCraftSlots().removeItem(i, a);
                itemstack = this.getCraftSlots().getItem(i);
            }
            if (!itemstack1.isEmpty()) {
                if (itemstack.isEmpty()) {
                    this.getCraftSlots().setItem(i, itemstack1);
                } else if (ItemStack.isSame(itemstack, itemstack1) && ItemStack.tagMatches(itemstack, itemstack1)) {
                    itemstack1.grow(itemstack.getCount());
                    this.getCraftSlots().setItem(i, itemstack1);
                } else if (!player.getInventory().add(itemstack1)) {
                    player.drop(itemstack1, false);
                }
            }
        }
    }

    @Accessor
    public abstract CraftingContainer getCraftSlots();
}
