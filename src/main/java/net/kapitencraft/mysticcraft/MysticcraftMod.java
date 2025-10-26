package net.kapitencraft.mysticcraft;

import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.config.CommonModConfig;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.registry.*;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import org.slf4j.Marker;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MysticcraftMod.MOD_ID)
public class MysticcraftMod {

    public static final String MOD_ID = "mysticcraft";

    public static ResourceLocation res(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    /**
     * use for custom registries on the Mysticcraft Namespace
     */
    public static <T> DeferredRegister<T> registry(ResourceKey<Registry<T>> key) {
        return DeferredRegister.create(key, MOD_ID);
    }

    public static final RandomSource RANDOM_SOURCE = RandomSource.create();
    public static final Logger LOGGER = LogUtils.getLogger();

    public MysticcraftMod(IEventBus modEventBus, ModContainer container) {

        ModCreativeModTabs.REGISTRY.register(modEventBus);
        ModDataComponentTypes.REGISTRY.register(modEventBus);
        ModItems.REGISTRY.register(modEventBus);
        ModMobEffects.REGISTRY.register(modEventBus);
        ModPotions.REGISTRY.register(modEventBus);
        ModBlockEntities.REGISTRY.register(modEventBus);
        ModBonusSerializers.REGISTRY.register(modEventBus);
        ModMenuTypes.REGISTRY.register(modEventBus);
        ModEntityTypes.REGISTRY.register(modEventBus);
        ModParticleTypes.REGISTRY.register(modEventBus);
        ModFluidTypes.REGISTRY.register(modEventBus);
        ModFluids.REGISTRY.register(modEventBus);
        ModBlocks.REGISTRY.register(modEventBus);
        ModVillagers.PROFESSION_REGISTRY.register(modEventBus);
        ModVillagers.POI_TYPE_REGISTRY.register(modEventBus);
        ModLootItemFunctions.REGISTRY.register(modEventBus);
        ModLootItemConditions.REGISTRY.register(modEventBus);
        ModStatTypes.REGISTRY.register(modEventBus);
        ModFeatures.REGISTRY.register(modEventBus);
        Spells.REGISTRY.register(modEventBus);
        ModInventoryPageTypes.REGISTRY.register(modEventBus);
        ModCommandArgumentTypes.REGISTRY.register(modEventBus);
        ModAttributes.REGISTRY.register(modEventBus);
        ModOverlays.REGISTRY.register(modEventBus);
        ModCooldowns.REGISTRY.register(modEventBus);
        ModTrunkPlacers.REGISTRY.register(modEventBus);
        ModFoliagePlacers.REGISTRY.register(modEventBus);
        ModRecipeTypes.REGISTRY.register(modEventBus);
        ModRecipeSerializers.REGISTRY.register(modEventBus);
        ModSensorTypes.REGISTRY.register(modEventBus);
        ModMemoryModuleTypes.REGISTRY.register(modEventBus);
        ModAttachmentTypes.REGISTRY.register(modEventBus);
        ModEntityEffectComponents.REGISTRY.register(modEventBus);
        ModArmorMaterials.REGISTRY.register(modEventBus);

        container.registerConfig(ModConfig.Type.CLIENT, ClientModConfig.SPEC);
        container.registerConfig(ModConfig.Type.COMMON, CommonModConfig.SPEC);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerModConfig.SPEC);

        
    }

    public static void sendRegisterDisplay(String nameOfRegistered, Marker marker) {
        LOGGER.debug(marker, "Registering {}...", nameOfRegistered);
    }
    public static void sendRegisterDisplay(String nameOfRegistered) {
        sendRegisterDisplay(nameOfRegistered, Markers.REGISTRY);
    }
}