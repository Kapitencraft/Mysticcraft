package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.inventory_page.page.InventoryPageType;
import net.kapitencraft.kap_lib.inventory_page.registry.custom.InventoryPageRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.skill.SkillsInventoryPage;
import net.kapitencraft.mysticcraft.rpg.traits.PerksInventoryPage;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModInventoryPageTypes {
    DeferredRegister<InventoryPageType<?>> REGISTRY = MysticcraftMod.registry(InventoryPageRegistries.Keys.INVENTORY_PAGES);

    Supplier<InventoryPageType<SkillsInventoryPage>> SKILLS = REGISTRY.register("skills", () -> new InventoryPageType<>(SkillsInventoryPage::new));
    Supplier<InventoryPageType<PerksInventoryPage>> PERKS = REGISTRY.register("attributes", () -> new InventoryPageType<>(PerksInventoryPage::new));
}
