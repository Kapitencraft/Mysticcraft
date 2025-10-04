package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.tech.gui.menu.*;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModMenuTypes {
    DeferredRegister<MenuType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.MENU_TYPES);

    RegistryObject<MenuType<ArtificerTableMenu>> GEM_GRINDER = registerContainerType("gem_grinder_menu", ArtificerTableMenu::new);
    RegistryObject<MenuType<ReforgeAnvilMenu>> REFORGING_ANVIL = registerContainerType("reforging_anvil", ReforgeAnvilMenu::new);
    RegistryObject<MenuType<PrismaticGeneratorMenu>> PRISMATIC_GENERATOR = registerMenuType("prismatic_generator", PrismaticGeneratorMenu::new);
    RegistryObject<MenuType<VulcanicGeneratorMenu>> VULCANIC_GENERATOR = registerMenuType("vulcanic_generator", VulcanicGeneratorMenu::new);
    RegistryObject<MenuType<MagicFurnaceMenu>> MAGIC_FURNACE = registerMenuType("magic_furnace", MagicFurnaceMenu::new);
    RegistryObject<MenuType<ManaBatteryMenu>> MANA_BATTERY = registerMenuType("mana_battery", ManaBatteryMenu::new);

    RegistryObject<MenuType<SpellCasterTurretMenu>> SPELL_CASTER_TURRET = registerMenuType("turret/spell_caster", SpellCasterTurretMenu::new);


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerContainerType(String name, MenuType.MenuSupplier<T> supplier) {
        return REGISTRY.register(name, () -> new MenuType<>(supplier, FeatureFlags.VANILLA_SET));
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, ()-> IForgeMenuType.create(factory));
    }
}
