package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.functions.ApplyEssenceTypeFunction;
import net.kapitencraft.mysticcraft.item.loot_table.functions.AttributeAmountModifierFunction;
import net.kapitencraft.mysticcraft.item.loot_table.functions.PristineFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface ModLootItemFunctions {
    DeferredRegister<LootItemFunctionType> REGISTRY = MysticcraftMod.makeRegistry(Registries.LOOT_FUNCTION_TYPE);

    RegistryObject<LootItemFunctionType> PRISTINE_MODIFIER = REGISTRY.register("pristine_modifier", type(PristineFunction.SERIALIZER));
    RegistryObject<LootItemFunctionType> ATTRIBUTE_MODIFIER = REGISTRY.register("attribute_modifier", type(AttributeAmountModifierFunction.SERIALIZER));
    RegistryObject<LootItemFunctionType> APPLY_ESSENCE_TYPE = REGISTRY.register("apply_essence", type(ApplyEssenceTypeFunction.SERIALIZER));

    private static Supplier<LootItemFunctionType> type(Serializer<? extends LootItemFunction> serializer) {
        return ()-> new LootItemFunctionType(serializer);
    }
}
