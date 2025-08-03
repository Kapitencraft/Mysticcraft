package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModRecipeSerializers {
    DeferredRegister<RecipeSerializer<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.RECIPE_SERIALIZERS);

    RegistryObject<RecipeSerializer<AltarRecipe>> ALTAR = REGISTRY.register("altar", AltarRecipe.Serializer::new);
}
