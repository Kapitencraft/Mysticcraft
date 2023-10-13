package net.kapitencraft.mysticcraft.block.entity.crafting;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface ModRecipeTypes {
    RecipeType<LargeCraftingRecipe> LARGE_CRAFTING = register("large_crafting");

    static <T extends Recipe<?>> RecipeType<T> register(final String name) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, MysticcraftMod.res(name), new RecipeType<T>() {
            public String toString() {
                return name;
            }
        });
    }

    static <T extends Recipe<?>> RecipeType<T> simple(ResourceLocation name) {
        final String toString = name.toString();
        return new RecipeType<T>() {
            public String toString() {
                return toString;
            }
        };
    }
}