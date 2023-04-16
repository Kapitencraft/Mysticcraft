package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        createArmor(consumer, ModItems.CRIMSON_ARMOR, ModItems.CRIMSON_STEEL_INGOT);
    }


    private static void  createArmor(Consumer<FinishedRecipe> recipe, HashMap<EquipmentSlot, RegistryObject<Item>> armor, RegistryObject<Item> material) {
        createHelmet(recipe, material, armor.get(EquipmentSlot.HEAD));
        createChestplate(recipe, material, armor.get(EquipmentSlot.CHEST));
        createLeggings(recipe, material, armor.get(EquipmentSlot.LEGS));
        createBoots(recipe, material, armor.get(EquipmentSlot.FEET));
    }
    private static void createHelmet(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("***").pattern("* *").define('*', material.get()).save(recipe);
    }
    private static void createChestplate(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("* *").pattern("***").pattern("***").define('*', material.get()).save(recipe);
    }
    private static void createLeggings(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("***").pattern("* *").pattern("* *").define('*', material.get()).save(recipe);
    }
    private static void createBoots(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("* *").pattern("* *").define('*', material.get()).save(recipe);
    }
}
