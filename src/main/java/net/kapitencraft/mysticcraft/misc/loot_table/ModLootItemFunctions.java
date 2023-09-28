package net.kapitencraft.mysticcraft.misc.loot_table;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class ModLootItemFunctions {
    public static LootItemFunctionType ATTRIBUTE_MODIFIER = minecraft("attribute_modifier", new AttributeAmountModifier.Serializer());

    private static LootItemFunctionType minecraft(String name, Serializer<? extends LootItemFunction> serializer) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(name), new LootItemFunctionType(serializer));
    }
}
