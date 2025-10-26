package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModRecipeSerializers {
    DeferredRegister<RecipeSerializer<?>> REGISTRY = MysticcraftMod.registry(Registries.RECIPE_SERIALIZER);

    Supplier<RecipeSerializer<AltarRecipe>> ALTAR = REGISTRY.register("altar", AltarRecipe.Serializer::new);
}
