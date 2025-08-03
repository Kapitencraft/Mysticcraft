package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.modifiers.AddEssenceModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public interface ModLootModifiers {
    DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    RegistryObject<Codec<AddEssenceModifier>> ADD_ESSENCE = REGISTRY.register("add_essence", ()-> AddEssenceModifier.CODEC);
}