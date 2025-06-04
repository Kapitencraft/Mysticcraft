package net.kapitencraft.mysticcraft.registry;

import com.google.common.base.Stopwatch;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class ModRegistryInit {
    private static IEventBus eventBus;
    private static int registered = 0;

    /**
     * loads all {@link DeferredRegister} in the mod registries classes
     * @param bus the event bus from the mod which
     */
    public static void register(IEventBus bus) {
        eventBus = bus;
        MysticcraftMod.LOGGER.info(Markers.REGISTRY, "starting registration");
        Stopwatch stopwatch = Stopwatch.createStarted();
        register("Creative Mode Tabs", ModCreativeModTabs.REGISTRY);
        register("Enchantments", ModEnchantments.REGISTRY);
        register("Items", ModItems.REGISTRY);
        register("Effects", ModMobEffects.REGISTRY);
        register("Potions", ModPotions.REGISTRY);
        register("Block Entities", ModBlockEntities.REGISTRY);
        register("Bonus Serializers", ModBonusSerializers.REGISTRY);
        register("Menus", ModMenuTypes.REGISTRY);
        register("Entity Types", ModEntityTypes.REGISTRY);
        register("Particle Types", ModParticleTypes.REGISTRY);
        register("Fluid Types", ModFluidTypes.REGISTRY);
        register("Fluids", ModFluids.REGISTRY);
        register("Blocks", ModBlocks.REGISTRY);
        register("Villager Professions", ModVillagers.PROFESSION_REGISTRY);
        register("POI Types", ModVillagers.POI_TYPE_REGISTRY);
        register("Loot Modifiers", ModLootModifiers.REGISTRY);
        register("Loot Functions", ModLootItemFunctions.REGISTRY);
        register("Loot Conditions", ModLootItemConditions.REGISTRY);
        register("Stat Types", ModStatTypes.REGISTRY);
        register("Features", ModFeatures.REGISTRY);
        register("Spells", Spells.REGISTRY);
        stopwatch.stop();
        MysticcraftMod.LOGGER.info(Markers.REGISTRY, "loading {} registries took {} ms", registered, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private static void register(String registerName, DeferredRegister<?> register) {
        if (eventBus == null) throw new IllegalStateException("Register while event is used");
        MysticcraftMod.sendRegisterDisplay(registerName);
        registered++;
        register.register(eventBus);
    }

}
