package net.kapitencraft.mysticcraft.init;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.*;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public interface ModLootModifiers {
    DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    RegistryObject<Codec<AddItemModifier>> ADD_ITEM = REGISTRY.register("add_item", ()-> AddItemModifier.CODEC);
    RegistryObject<Codec<EnchantmentAddItemModifier>> ENCH_ADD_ITEM = REGISTRY.register("ench_add_item", ()-> EnchantmentAddItemModifier.CODEC);
    RegistryObject<Codec<TelekinesisModifier>> TELEKINESIS = REGISTRY.register("telekinesis", ()-> TelekinesisModifier.CODEC);
    RegistryObject<Codec<ReplenishModifier>> REPLENISH = REGISTRY.register("replenish", ()-> ReplenishModifier.CODEC);
    RegistryObject<Codec<OreModifier>> ORE = REGISTRY.register("ore_mod", ()-> OreModifier.CODEC);
    RegistryObject<Codec<SmeltModifier>> SMELT = REGISTRY.register("smelt", ()-> SmeltModifier.CODEC);
    RegistryObject<Codec<AddEssenceModifier>> ADD_ESSENCE = REGISTRY.register("add_essence", ()-> AddEssenceModifier.CODEC);
}