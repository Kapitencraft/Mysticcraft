package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.storage.loot_table.functions.AttributeAmountModifierFunction;
import net.kapitencraft.mysticcraft.item.storage.loot_table.functions.PristineConditionFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface ModLootItemFunctions {
    DeferredRegister<LootItemFunctionType> REGISTRY = MysticcraftMod.makeRegistry(Registries.LOOT_FUNCTION_TYPE);
    RegistryObject<LootItemFunctionType> PRISTINE_MODIFIER = REGISTRY.register("attribute_modifier", type(AttributeAmountModifierFunction.Serializer::new));

    RegistryObject<LootItemFunctionType> ATTRIBUTE_MODIFIER = REGISTRY.register("pristine_modifier", type(PristineConditionFunction.Serializer::new));

    private static Supplier<LootItemFunctionType> type(Supplier<Serializer<? extends LootItemFunction>> serializer) {
        return ()-> new LootItemFunctionType(serializer.get());
    }
}