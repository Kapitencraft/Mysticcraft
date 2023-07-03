package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgingAnvilMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MysticcraftMod.MOD_ID);

    public static final RegistryObject<MenuType<GemstoneGrinderMenu>> GEM_GRINDER = registerMenuType(GemstoneGrinderMenu::new, "gem_grinder_menu");
    public static final RegistryObject<MenuType<ReforgingAnvilMenu>> REFORGING_ANVIL = registerMenuType(ReforgingAnvilMenu::new, "reforging_anvil");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return REGISTRY.register(name, ()-> IForgeMenuType.create(factory));
    }
}
