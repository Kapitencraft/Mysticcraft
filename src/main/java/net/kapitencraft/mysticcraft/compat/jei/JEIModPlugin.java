package net.kapitencraft.mysticcraft.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.crafting.ModRecipeTypes;
import net.kapitencraft.mysticcraft.block.entity.crafting.serializers.ArmorRecipe;
import net.kapitencraft.mysticcraft.block.entity.crafting.serializers.UpgradeItemRecipe;
import net.kapitencraft.mysticcraft.compat.jei.crafting.UpgradeItemExtension;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return MysticcraftMod.res("jei_plugin");
    }

    @SuppressWarnings("all")
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(RecipeTypes.CRAFTING, manager.getAllRecipesFor(ModRecipeTypes.ARMOR_RECIPE.get()).stream()
                .map(ArmorRecipe::getAll).collect(CollectorHelper.merge()).map(MiscHelper.instanceMapper(CraftingRecipe.class)).toList()
        );
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IExtendableRecipeCategory<CraftingRecipe, ICraftingCategoryExtension> category = registration.getCraftingCategory();
        category.addCategoryExtension(UpgradeItemRecipe.class, UpgradeItemExtension::new);
    }
}
