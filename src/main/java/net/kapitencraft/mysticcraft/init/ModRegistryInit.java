package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

@Mod.EventBusSubscriber
public class ModRegistryInit {
    private static IEventBus eventBus;

    public static void register(IEventBus bus) {
        eventBus = bus;
        register("Attributes", ModAttributes.REGISTRY);
        register("Enchantments", ModEnchantments.REGISTRY);
        register("Items", ModItems.REGISTRY);
        register("Effects", ModMobEffects.REGISTRY);
        register("Potions", ModPotions.REGISTRY);
        register("Block Entities", ModBlockEntities.REGISTRY);
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
    }

    private static void register(String registerName, DeferredRegister<?> register) {
        if (eventBus == null) throw new IllegalStateException("Register while event is used");
        MysticcraftMod.sendRegisterDisplay(registerName);
        register.register(eventBus);
    }
}
