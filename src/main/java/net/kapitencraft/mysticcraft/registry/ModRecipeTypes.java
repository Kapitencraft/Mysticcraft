package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModRecipeTypes {
    DeferredRegister<RecipeType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.RECIPE_TYPES);

    RegistryObject<RecipeType<AltarRecipe>> ALTAR = REGISTRY.register("altar", () -> new RecipeType<>() {});
}
