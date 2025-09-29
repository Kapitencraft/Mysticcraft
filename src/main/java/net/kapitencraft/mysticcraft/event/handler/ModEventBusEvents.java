package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.kapitencraft.mysticcraft.event.advancement.ModCriteriaTriggers;
import net.kapitencraft.mysticcraft.event.custom.AddGemstonesToItemEvent;
import net.kapitencraft.mysticcraft.event.custom.RegisterGemstoneTypePlacementsEvent;
import net.kapitencraft.mysticcraft.item.misc.AnvilUses;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.potion.ModPotionRecipe;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistryBuilders;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(new ModPotionRecipe());
        Reforges.registerRarities();
        Reforges.bootstrap();
        AnvilUses.registerUses();
        ModMessages.register();
        ModCriteriaTriggers.init();
    }

    @SubscribeEvent
    public void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(ModEntityTypes.FROZEN_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntityTypes.VAMPIRE_BAT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }


    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.create(ModRegistryBuilders.SPELLS);
        event.create(ModRegistryBuilders.PERK_REWARDS);
        MysticcraftMod.LOGGER.info(Markers.REGISTRY, "Registered custom registries");
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
    public static void registerGemstoneItems(AddGemstonesToItemEvent event) {
        event.registerArmor(ModItems.ENDER_KNIGHT_ARMOR, new GemstoneSlot.Builder(GemstoneSlot.Type.DEFENCE, GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH));
        event.register(ModItems.VALKYRIE, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH));
        event.register(ModItems.HYPERION, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.INTELLIGENCE));
        event.register(ModItems.SCYLLA, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT));
        event.register(ModItems.ASTREA, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.DEFENCE));
        event.register(ModItems.NECRON_SWORD, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT));
        event.register(ModItems.LONGBOW, new GemstoneSlot.Builder(GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.DRAW_SPEED));
        event.register(ModItems.MANA_STEEL_SWORD, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT));
        event.register(ModItems.LAVA_FISHING_ROD_TEST, new GemstoneSlot.Builder(GemstoneSlot.Type.FISHING_SPEED));
        event.register(ModItems.TRAVELERS_BOOTS, new GemstoneSlot.Builder(GemstoneSlot.Type.MOBILITY, GemstoneSlot.Type.MOBILITY));
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.VAMPIRE_BAT.get(), VampireBat.createAttributes().build());
        event.put(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeEntity.createAttributes().build());
        event.put(ModEntityTypes.DRAGON.get(), Dragon.createAttributes().build());
    }
}
