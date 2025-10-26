package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.inventory.page.InventoryPageType;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.skill.SkillsInventoryPage;
import net.kapitencraft.mysticcraft.rpg.traits.TraitsInventoryPage;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModInventoryPageTypes {
    DeferredRegister<InventoryPageType<?>> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.INVENTORY_PAGES);

    Supplier<InventoryPageType<SkillsInventoryPage>> SKILLS = REGISTRY.register("skills", () -> new InventoryPageType<>(SkillsInventoryPage::new));
    Supplier<InventoryPageType<TraitsInventoryPage>> ATTRIBUTES = REGISTRY.register("attributes", () -> new InventoryPageType<>(TraitsInventoryPage::new));
}
