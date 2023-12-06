package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.crafting.serializers.ArmorRecipe;
import net.kapitencraft.mysticcraft.block.entity.crafting.serializers.UpgradeItemRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModRecipeSerializers {
    DeferredRegister<RecipeSerializer<?>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.RECIPE_SERIALIZERS);

    RegistryObject<RecipeSerializer<UpgradeItemRecipe>> UPGRADE_ITEM = REGISTRY.register("upgrade_item", UpgradeItemRecipe.Serializer::new);
    RegistryObject<RecipeSerializer<ArmorRecipe>> ARMOR = REGISTRY.register("armor", ArmorRecipe.Serializer::new);
}
