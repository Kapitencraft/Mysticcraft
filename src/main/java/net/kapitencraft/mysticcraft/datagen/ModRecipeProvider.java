package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
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
        makeModel((like, ingredient) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, like).pattern("***").pattern("*.*").pattern("***").define('*', ingredient).define('.', ModItems.NECRON_SWORD.get()), ModItems.HYPERION.get(), ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.STORM).get(), consumer);
        makeModel((like, ingredient) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, like).pattern("***").pattern("*.*").pattern("***").define('*', ingredient).define('.', ModItems.NECRON_SWORD.get()), ModItems.VALKYRIE.get(), ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.NECRON).get(), consumer);
        makeModel((like, ingredient) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, like).pattern("***").pattern("*.*").pattern("***").define('*', ingredient).define('.', ModItems.NECRON_SWORD.get()), ModItems.ASTREA.get(), ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.GOLDOR).get(), consumer);
        makeModel((like, ingredient) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, like).pattern("***").pattern("*.*").pattern("***").define('*', ingredient).define('.', ModItems.NECRON_SWORD.get()), ModItems.SCYLLA.get(), ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.MAXOR).get(), consumer);
        makeModel((like, ingredient) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, like).pattern("*").pattern("*").pattern(".").define('.', ingredient).define('*', Items.WITHER_SKELETON_SKULL), ModItems.NECRON_SWORD.get(), ModItems.NECRONS_HANDLE.get(), consumer);
    }

    private static final BuilderConsumer SLAB_CONSUMER = (like, ingredient) -> slabBuilder(RecipeCategory.BUILDING_BLOCKS, like, ingredient);
    private static final BuilderConsumer WALL_CONSUMER = (like, ingredient) -> wallBuilder(RecipeCategory.BUILDING_BLOCKS, like, ingredient);

    private static void makeModel(BuilderConsumer consumer, Item result, Item material, Consumer<FinishedRecipe> finishedRecipeConsumer) {
        unlock(consumer.use(result, Ingredient.of(material)), result, material).save(finishedRecipeConsumer);
    }

    private interface BuilderConsumer {
        RecipeBuilder use(ItemLike like, Ingredient ingredient);
    }

    private static RecipeBuilder unlock(RecipeBuilder recipeBuilder, Item result, Item material) {
        return recipeBuilder.unlockedBy(getHasName(result), has(material));
    }

    private static void  createArmor(Consumer<FinishedRecipe> recipe, HashMap<EquipmentSlot, RegistryObject<Item>> armor, RegistryObject<Item> material) {
        createHelmet(recipe, material, armor.get(EquipmentSlot.HEAD));
        createChestplate(recipe, material, armor.get(EquipmentSlot.CHEST));
        createLeggings(recipe, material, armor.get(EquipmentSlot.LEGS));
        createBoots(recipe, material, armor.get(EquipmentSlot.FEET));
    }
    private static void createHelmet(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("***").pattern("* *").define('*', material.get()).unlockedBy(getHasName(result.get()), has(material.get())).save(recipe);
    }
    private static void createChestplate(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("* *").pattern("***").pattern("***").define('*', material.get()).unlockedBy(getHasName(result.get()), has(material.get())).save(recipe);
    }
    private static void createLeggings(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("***").pattern("* *").pattern("* *").define('*', material.get()).unlockedBy(getHasName(result.get()), has(material.get())).save(recipe);
    }
    private static void createBoots(Consumer<FinishedRecipe> recipe, RegistryObject<Item> material, RegistryObject<Item> result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern("* *").pattern("* *").define('*', material.get()).unlockedBy(getHasName(result.get()), has(material.get())).save(recipe);
    }

    private static void hammerCrafting(Consumer<FinishedRecipe> consumer, TagKey<Item> hammerTier, RegistryObject<Item> material, RegistryObject<Item> result, int amount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount).requires(hammerTier).requires(material.get()).unlockedBy(getHasName(result.get()), has(material.get())).save(consumer);
    }
}
