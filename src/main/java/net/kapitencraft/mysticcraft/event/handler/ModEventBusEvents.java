package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.crafting.ItemAmountIngredient;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.item.misc.AnvilUses;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.potion.ModPotionRecipe;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import static net.kapitencraft.mysticcraft.MysticcraftMod.sendRegisterDisplay;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        sendRegisterDisplay("custom Potion Recipes");
        BrewingRecipeRegistry.addRecipe(new ModPotionRecipe());
        sendRegisterDisplay("Entity World Generation");
        registerSpawnPlacements();
        sendRegisterDisplay("Rarities");
        Reforges.registerRarities();
        sendRegisterDisplay("Reforges");
        Reforges.register();
        sendRegisterDisplay("Anvil Uses");
        AnvilUses.registerUses();
        sendRegisterDisplay("Packet Handling");
        ModMessages.register();
    }

    @SubscribeEvent
    public static void registerRecipeAdditions(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
            sendRegisterDisplay("Recipe Serializers");
            CraftingHelper.register(MysticcraftMod.res("amount"), ItemAmountIngredient.Serializer.INSTANCE);
        }
    }


    private static void registerSpawnPlacements() {
        SpawnPlacements.register(ModEntityTypes.FROZEN_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.VAMPIRE_BAT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules);
    }
}
