package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModRecipeTypes {
    DeferredRegister<RecipeType<?>> REGISTRY = MysticcraftMod.registry(Registries.RECIPE_TYPE);

    Supplier<RecipeType<AltarRecipe>> ALTAR = REGISTRY.register("altar", () -> new RecipeType<>() {});
}
