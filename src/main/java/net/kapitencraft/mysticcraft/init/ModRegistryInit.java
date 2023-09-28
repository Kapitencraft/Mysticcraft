package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModRegistryInit {

    public static void register(IEventBus bus) {
        MysticcraftMod.sendRegisterDisplay("Attributes");
        ModAttributes.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Enchantments");
        ModEnchantments.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Items");
        ModItems.register(bus);
        MysticcraftMod.sendRegisterDisplay("Effects");
        ModMobEffects.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Potions");
        ModPotions.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Entities");
        ModBlockEntities.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Menus");
        ModMenuTypes.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Entity Types");
        ModEntityTypes.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Particle Types");
        ModParticleTypes.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Fluid Types");
        ModFluidTypes.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Fluids");
        ModFluids.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Blocks");
        ModBlocks.REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("Villager Professions");
        ModVillagers.PROFESSION_REGISTRY.register(bus);
        MysticcraftMod.sendRegisterDisplay("POI Types");
        ModVillagers.POI_TYPE_REGISTRY.register(bus);
    }
}
