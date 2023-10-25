package net.kapitencraft.mysticcraft.misc.loot_table;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.loot_table.functions.AttributeAmountModifier;
import net.kapitencraft.mysticcraft.misc.loot_table.functions.PristineConditionFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModLootItemFunctions {
    public static LootItemFunctionType ATTRIBUTE_MODIFIER;
    public static LootItemFunctionType PRISTINE_MODIFIER;

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        MysticcraftMod.sendRegisterDisplay("Loot Item Functions");
        ATTRIBUTE_MODIFIER = minecraft("attribute_modifier", new AttributeAmountModifier.Serializer());
        PRISTINE_MODIFIER = mysticcraft("pristine_modifier", new PristineConditionFunction.Serializer());
    }

    private static LootItemFunctionType minecraft(String name, Serializer<? extends LootItemFunction> serializer) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(name), new LootItemFunctionType(serializer));
    }

    private static LootItemFunctionType mysticcraft(String name, Serializer<? extends LootItemFunction> serializer) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, MysticcraftMod.res(name), new LootItemFunctionType(serializer));
    }
}
