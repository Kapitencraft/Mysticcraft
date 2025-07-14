package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.inventory.page.InventoryPageType;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.perks.PerkInventoryPage;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModInventoryPages {
    DeferredRegister<InventoryPageType<?>> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.INVENTORY_PAGES);

    RegistryObject<InventoryPageType<PerkInventoryPage>> PERKS = REGISTRY.register("perks", () -> new InventoryPageType<>(PerkInventoryPage::new));
}
