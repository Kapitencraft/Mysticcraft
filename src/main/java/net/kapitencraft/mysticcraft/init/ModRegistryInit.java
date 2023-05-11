package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModRegistryInit {

    public static void register(IEventBus bus) {
        MysticcraftMod.sendInfo("Registering Attributes...");
        ModAttributes.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Enchantments...");
        ModEnchantments.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Items...");
        ModItems.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Effects...");
        ModMobEffects.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Block Entities...");
        ModBlockEntities.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Menus...");
        ModMenuTypes.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Entity Types...");
        ModEntityTypes.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Particle Types...");
        ModParticleTypes.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Fluid Types...");
        ModFluidTypes.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Fluids...");
        ModFluids.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Blocks...");
        ModBlocks.REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering Villager Professions");
        ModVillagers.PROFESSION_REGISTRY.register(bus);
        MysticcraftMod.sendInfo("Registering POI-Types");
        ModVillagers.REGISTRY.register(bus);
    }
}
