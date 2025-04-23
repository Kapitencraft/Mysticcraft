package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.AddEssenceModifier;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.AddItemModifier;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.EnchantmentAddItemModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public interface ModLootModifiers {
    DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    RegistryObject<Codec<AddItemModifier>> ADD_ITEM = REGISTRY.register("add_item", ()-> AddItemModifier.CODEC);
    RegistryObject<Codec<EnchantmentAddItemModifier>> ENCH_ADD_ITEM = REGISTRY.register("ench_add_item", ()-> EnchantmentAddItemModifier.CODEC);
    RegistryObject<Codec<AddEssenceModifier>> ADD_ESSENCE = REGISTRY.register("add_essence", ()-> AddEssenceModifier.CODEC);
}