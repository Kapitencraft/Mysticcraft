package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.functions.PristineFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModLootItemFunctions {
    DeferredRegister<LootItemFunctionType<?>> REGISTRY = MysticcraftMod.registry(Registries.LOOT_FUNCTION_TYPE);

    Supplier<LootItemFunctionType<PristineFunction>> PRISTINE_MODIFIER = REGISTRY.register("pristine_modifier", type(PristineFunction.CODEC));

    private static <T extends LootItemFunction> Supplier<LootItemFunctionType<T>> type(MapCodec<T> serializer) {
        return ()-> new LootItemFunctionType<>(serializer);
    }
}
