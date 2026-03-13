package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.recipe.datagen.ArmorRecipeBuilder;
import net.kapitencraft.kap_lib.recipe.datagen.UpgradeRecipeBuilder;
import net.kapitencraft.kap_lib.recipe.serializers.UpgradeItemRecipe;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarRecipe;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.ItemGemstoneData;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ArmorRecipeBuilder.create(ModItems.CRIMSON_ARMOR).material(ModItems.CRIMSON_STEEL_INGOT).save(recipeOutput, "mysticcraft:crimson_armor");
        ArmorRecipeBuilder.create(ModItems.FROZEN_BLAZE_ARMOR).material(ModItems.FROZEN_BLAZE_ROD).save(recipeOutput, "mysticcraft:frozen_blaze_armor");
        ArmorRecipeBuilder.create(ModItems.SOUL_MAGE_ARMOR).material(ModBlocks.SOUL_CHAIN.item()).save(recipeOutput, "mysticcraft:soul_mage_armor");
        unlock(ArmorRecipeBuilder.create(ModItems.SHADOW_ASSASSIN_ARMOR).material(
                DataComponentIngredient.of(false, DataComponents.DYED_COLOR, new DyedItemColor(3355189, true), ModItems.DYED_LEATHER.get())
        ), ModItems.DYED_LEATHER).save(recipeOutput, "mysticcraft:shadow_assassin_armor"); //TODO

        itemUpgrade(recipeOutput, RecipeCategory.COMBAT, UpgradeItemRecipe.CraftType.EIGHT, ModItems.ASTREA, ModItems.NECRON_SWORD, ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.GOLDOR));
        itemUpgrade(recipeOutput, RecipeCategory.COMBAT, UpgradeItemRecipe.CraftType.EIGHT, ModItems.HYPERION, ModItems.NECRON_SWORD, ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.STORM));
        itemUpgrade(recipeOutput, RecipeCategory.COMBAT, UpgradeItemRecipe.CraftType.EIGHT, ModItems.SCYLLA, ModItems.NECRON_SWORD, ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.MAXOR));
        itemUpgrade(recipeOutput, RecipeCategory.COMBAT, UpgradeItemRecipe.CraftType.EIGHT, ModItems.VALKYRIE, ModItems.NECRON_SWORD, ModItems.PRECURSOR_RELICTS.get(PrecursorRelicItem.BossType.NECRON));

        itemUpgrade(recipeOutput, RecipeCategory.COMBAT, UpgradeItemRecipe.CraftType.EIGHT, ModItems.SHADOW_DAGGER, ModItems.DARK_DAGGER, ModItems.SHADOW_CRYSTAL);

        craftHammer(recipeOutput, Items.COBBLESTONE, Items.STONE, ModItems.STONE_HAMMER);
        craftHammer(recipeOutput, Items.IRON_INGOT, Items.IRON_BLOCK, ModItems.IRON_HAMMER);
        craftHammer(recipeOutput, Items.DIAMOND, Items.DIAMOND_BLOCK, ModItems.DIAMOND_HAMMER);

        modNetheriteSmithing(recipeOutput, ModItems.DIAMOND_HAMMER.get(), RecipeCategory.TOOLS, ModItems.NETHERITE_HAMMER.get());

        hammerCrushing(recipeOutput, ModTags.Items.TIER_1_HAMMER, ModItems.CRIMSONIUM_INGOT, ModItems.CRIMSONIUM_DUST, 1);
        hammerCrushing(recipeOutput, ModTags.Items.HAMMER, ModItems.CRIMSONITE_CLUSTER, ModItems.CRIMSONITE_DUST, 2);
        hammerCrushing(recipeOutput, ModTags.Items.HAMMER, ModItems.RAW_CRIMSONIUM, ModItems.RAW_CRIMSONIUM_DUST, 2);
        hammerCrushing(recipeOutput, ModTags.Items.HAMMER, Items.COBBLESTONE, Items.GRAVEL, 1, MysticcraftMod.res("crushing/"));
        hammerCrushing(recipeOutput, ModTags.Items.HAMMER, Items.GRAVEL, Items.SAND, 1, MysticcraftMod.res("crushing/sand"));
        hammerCrushing(recipeOutput, ModTags.Items.TIER_1_HAMMER, Items.LAPIS_LAZULI, ModItems.LAPIS_DUST.get(), 1);

        unlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CRIMSON_STEEL_DUST.get(), 2)
                        .group("crimson")
                        .requires(ModItems.CRIMSONIUM_DUST.get())
                        .requires(ModItems.CRIMSONITE_DUST.get()),
                ModItems.CRIMSONIUM_DUST.get()
        ).save(recipeOutput);

        smeltingAndBlasting(recipeOutput, List.of(ModItems.RAW_CRIMSONIUM.get(), ModItems.RAW_CRIMSONIUM_DUST.get()), RecipeCategory.MISC, ModItems.CRIMSONIUM_INGOT.get(), 1.2f, 100, "crimson");
        smeltingAndBlasting(recipeOutput, List.of(ModItems.CRIMSON_STEEL_DUST.get()), RecipeCategory.MISC, ModItems.CRIMSON_STEEL_INGOT.get(), 1.3f, 200, "crimson");

        makeModel(SLAB_CONSUMER, ModBlocks.GOLDEN_SLAB.getItem(), Items.GOLD_BLOCK, recipeOutput);
        makeModel(WALL_CONSUMER, ModBlocks.GOLDEN_WALL.getItem(), Items.GOLD_BLOCK, recipeOutput);
        makeModel(RecipeProvider::stairBuilder, ModBlocks.GOLDEN_STAIRS.getItem(), Items.GOLD_BLOCK, recipeOutput);

        modStonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLDEN_SLAB.getItem(), Items.GOLD_BLOCK, 2);
        modStonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLDEN_WALL.getItem(), Items.GOLD_BLOCK);
        modStonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLDEN_STAIRS.getItem(), Items.GOLD_BLOCK);
        modStonecutterResultFromBase(recipeOutput, RecipeCategory.REDSTONE, ModBlocks.LAPIS_BUTTON.get(), Items.LAPIS_BLOCK);

        craftBow(recipeOutput, Items.STRING, ModTags.Items.STRIPPED_LOGS, ModItems.LONGBOW);

        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PERIDOT_SYCAMORE_WOOD.getItem(), ModBlocks.PERIDOT_SYCAMORE_LOG.getItem());
        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_PERIDOT_SYCAMORE_WOOD.getItem(), ModBlocks.STRIPPED_PERIDOT_SYCAMORE_LOG.getItem());

        //region mana steel sword
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MS_HANDLE.get())
                        .pattern("  *")
                        .pattern("** ")
                        .pattern("+* ")
                        .define('*', ModItems.MANA_STEEL_INGOT.get())
                        .define('+', DataComponentIngredient.of(false, ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.PERFECT), ModItems.GEMSTONE)),
                ModItems.MANA_STEEL_INGOT.get()
        ).save(recipeOutput);
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MS_UPPER_BlADE.get())
                        .pattern(" * ")
                        .pattern(" * ")
                        .pattern("*+*")
                        .define('*', ModItems.MANA_STEEL_INGOT.get())
                        .define('+', DataComponentIngredient.of(false, ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.PERFECT), ModItems.GEMSTONE)),
                ModItems.MANA_STEEL_INGOT.get()
        ).save(recipeOutput);
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MS_DOWN_BlADE.get())
                        .pattern(" * ")
                        .pattern("*+*")
                        .pattern("*+*")
                        .define('*', ModItems.MANA_STEEL_INGOT.get())
                        .define('+', DataComponentIngredient.of(false,  ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.JASPER, GemstoneType.Rarity.PERFECT), ModItems.GEMSTONE)),
                ModItems.MANA_STEEL_INGOT.get()
        ).save(recipeOutput);
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MANA_STEEL_SWORD.get())
                        .pattern("  +")
                        .pattern(" * ")
                        .pattern("#  ")
                        .define('+', ModItems.MS_UPPER_BlADE.get())
                        .define('*', ModItems.MS_DOWN_BlADE.get())
                        .define('#', ModItems.MS_HANDLE.get()),
                ModItems.MANA_STEEL_INGOT.get()
        ).save(recipeOutput);
        //endregion

        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NECRON_SWORD.get())
                        .pattern("*")
                        .pattern("*")
                        .pattern("+")
                        .define('*', Items.WITHER_SKELETON_SKULL)
                        .define('+', Items.STICK),
                Items.WITHER_SKELETON_SKULL
        ).save(recipeOutput);

        unlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PERIDOT_SYCAMORE_PLANKS.getItem(), 4)
                        .requires(ModBlocks.PERIDOT_SYCAMORE_LOG.getItem()).group("planks"),
                ModBlocks.PERIDOT_SYCAMORE_LOG.getItem()
        ).save(recipeOutput);

        AltarRecipe
                .builder(new ItemStack(ModItems.VOID_TOTEM_ITEM.get()), RecipeCategory.COMBAT)
                .middleIngredient(Ingredient.of(Items.TOTEM_OF_UNDYING))
                .allIngredient(Ingredient.of(Items.ENDER_PEARL))
                .unlockedBy(getHasName(Items.TOTEM_OF_UNDYING), has(Items.TOTEM_OF_UNDYING))
                .save(recipeOutput);
    }

    private static final BuilderConsumer SLAB_CONSUMER = (like, ingredient) -> slabBuilder(RecipeCategory.BUILDING_BLOCKS, like, ingredient);
    private static final BuilderConsumer WALL_CONSUMER = (like, ingredient) -> wallBuilder(RecipeCategory.BUILDING_BLOCKS, like, ingredient);

    private static void makeModel(BuilderConsumer consumer, Item result, Item material, RecipeOutput finishedRecipeConsumer) {
        unlock(consumer.use(result, Ingredient.of(material)), material).save(finishedRecipeConsumer);
    }

    private static void itemUpgrade(RecipeOutput consumer, RecipeCategory category, UpgradeItemRecipe.CraftType craftType, DeferredItem<? extends Item> result, DeferredItem<? extends Item> source, DeferredItem<? extends Item> material) {
        UpgradeRecipeBuilder.create(category, craftType, result.get()).source(source).material(material).unlockedBy(getHasName(material.get()), has(material.get())).save(consumer);
    }

    private interface BuilderConsumer {
        RecipeBuilder use(ItemLike like, Ingredient ingredient);
    }

    private static RecipeBuilder unlock(RecipeBuilder recipeBuilder, Item material) {
        return recipeBuilder.unlockedBy(getHasName(material), has(material));
    }

    private static RecipeBuilder unlock(RecipeBuilder builder, DeferredItem<?> material) {
        return unlock(builder, material.get());
    }

    private static void smeltingAndBlasting(RecipeOutput consumer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float xp, int blastTime, String group) {
        modOreSmelting(consumer, ingredients, category, result, xp, blastTime * 2, group);
        modOreBlasting(consumer, ingredients, category, result, xp, blastTime, group);
    }

    private static void hammerCrushing(RecipeOutput consumer, TagKey<Item> hammerTier, DeferredItem<? extends Item> material, DeferredItem<? extends Item> result, int amount) {
        hammerCrushing(consumer, hammerTier, material.get(), result.get(), amount);
    }

    private static void hammerCrushing(RecipeOutput consumer, TagKey<Item> hammerTier, Item material, Item result, int amount, ResourceLocation location) {
        unlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, amount).requires(hammerTier).requires(material), material).save(consumer, location);
    }

    private static void hammerCrushing(RecipeOutput consumer, TagKey<Item> hammerTier, DeferredItem<? extends Item> material, DeferredItem<? extends Item> result, int amount, ResourceLocation location) {
        hammerCrushing(consumer, hammerTier, material.get(), result.get(), amount, location);
    }

    private static void hammerCrushing(RecipeOutput consumer, TagKey<Item> hammerTier, Item material, Item result, int amount) {
        unlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, amount).requires(hammerTier).requires(material), material).save(consumer);
    }


    private static void craftHammer(RecipeOutput consumer, Item material, Item materialBlock, DeferredItem<? extends Item> result) {
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("#*#")
                .pattern(" ! ")
                .pattern(" ! ")
                .define('*', material)
                .define('#', materialBlock)
                .define('!', Items.STICK), material).save(consumer);
    }

    private static void craftBow(RecipeOutput consumer, Item string, Item handle, DeferredItem<? extends Item> result) {
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern(" *#").pattern("* #").pattern(" *#").define('*', string).define('#', handle), string).save(consumer);
    }

    private static void craftBow(RecipeOutput consumer, Item string, TagKey<Item> handle, DeferredItem<? extends Item> result) {
        unlock(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get()).pattern(" *#").pattern("* #").pattern(" *#").define('*', string).define('#', handle), string).save(consumer);
    }


    private static void modOreSmelting(RecipeOutput pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        modOreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    private static void modOreBlasting(RecipeOutput pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        modOreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    private static <T extends AbstractCookingRecipe> void modOreCooking(RecipeOutput pFinishedRecipeConsumer, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, MysticcraftMod.res(getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)));
        }
    }

    private static void modNetheriteSmithing(RecipeOutput pFinishedRecipeConsumer, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pCategory, pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, MysticcraftMod.res(getItemName(pResultItem) + "_smithing"));
    }

    private static void modStonecutterResultFromBase(RecipeOutput pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, ItemLike pMaterial) {
        modStonecutterResultFromBase(pFinishedRecipeConsumer, pCategory, pResult, pMaterial, 1);
    }

    private static void modStonecutterResultFromBase(RecipeOutput pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, ItemLike pMaterial, int pResultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), pCategory, pResult, pResultCount).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, MysticcraftMod.res(getConversionRecipeName(pResult, pMaterial) + "_stonecutting"));
    }
}
