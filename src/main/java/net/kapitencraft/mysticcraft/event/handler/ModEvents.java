package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.kapitencraft.mysticcraft.event.custom.RegisterGemstoneTypePlacementsEvent;
import net.kapitencraft.mysticcraft.item.misc.AnvilUses;
import net.kapitencraft.mysticcraft.potion.ModPotionRecipe;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.classes.RPGClass;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        Reforges.registerRarities();
        Reforges.bootstrap();
        AnvilUses.registerUses();
    }

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(new ModPotionRecipe());
    }


    @SubscribeEvent
    public static void onSpawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntityTypes.FROZEN_BLAZE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntityTypes.VAMPIRE_BAT.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(ModRegistries.SPELLS);
    }

    @SubscribeEvent
    public static void onDataPackRegistryNewRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(ModRegistries.Keys.CLASSES, RPGClass.DIRECT_CODEC);
    }


    @SubscribeEvent
    public static void registerGemstoneValidations(RegisterGemstoneTypePlacementsEvent event) {
        event.addValidation((biome) -> biome.is(Biomes.OCEAN), GemstoneType.AQUAMARINE, GemstoneType.TURQUOISE);
        event.addValidation((biome) -> biome.is(Biomes.FOREST), GemstoneType.CELESTINE);
        event.addValidation((biome) -> biome.is(Tags.Biomes.IS_MOUNTAIN), GemstoneType.AMETHYST, GemstoneType.SAPPHIRE);
        event.addValidation((biome) -> biome.is(Biomes.END_HIGHLANDS), GemstoneType.ALMANDINE);
        event.addValidation((biome) -> biome.is(Biomes.BASALT_DELTAS), GemstoneType.JASPER);
        event.addValidation((biome) -> biome.is(BiomeTags.IS_OVERWORLD), GemstoneType.RUBY);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.VAMPIRE_BAT.get(), VampireBat.createAttributes().build());
        event.put(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeEntity.createAttributes().build());
        event.put(ModEntityTypes.DRAGON.get(), Dragon.createAttributes().build());
    }
}
