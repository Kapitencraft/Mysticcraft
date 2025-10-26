package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.tech.gui.menu.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModMenuTypes {
    DeferredRegister<MenuType<?>> REGISTRY = MysticcraftMod.registry(Registries.MENU);

    Supplier<MenuType<ArtificerTableMenu>> GEM_GRINDER = registerContainerType("gem_grinder_menu", ArtificerTableMenu::new);
    Supplier<MenuType<ReforgeAnvilMenu>> REFORGING_ANVIL = registerContainerType("reforging_anvil", ReforgeAnvilMenu::new);
    Supplier<MenuType<PrismaticGeneratorMenu>> PRISMATIC_GENERATOR = registerMenuType("prismatic_generator", PrismaticGeneratorMenu::new);
    Supplier<MenuType<VulcanicGeneratorMenu>> VULCANIC_GENERATOR = registerMenuType("vulcanic_generator", VulcanicGeneratorMenu::new);
    Supplier<MenuType<MagicFurnaceMenu>> MAGIC_FURNACE = registerMenuType("magic_furnace", MagicFurnaceMenu::new);
    Supplier<MenuType<ManaBatteryMenu>> MANA_BATTERY = registerMenuType("mana_battery", ManaBatteryMenu::new);

    Supplier<MenuType<SpellCasterTurretMenu>> SPELL_CASTER_TURRET = registerMenuType("turret/spell_caster", SpellCasterTurretMenu::new);
    Supplier<MenuType<ObeliskTurretMenu>> OBELISK_TURRET = registerMenuType("turret/obelisk", ObeliskTurretMenu::new);


    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerContainerType(String name, MenuType.MenuSupplier<T> supplier) {
        return REGISTRY.register(name, () -> new MenuType<>(supplier, FeatureFlags.VANILLA_SET));
    }

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, ()-> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }
}
