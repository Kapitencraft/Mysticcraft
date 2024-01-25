package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.requirements.type.StatReqType;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public interface ModRequirements {
    DeferredRegister<Requirement<?>> REGISTRY = MysticcraftMod.makeCustomRegistry(ModRegistryKeys.REQUIREMENTS, RegistryBuilder::new);

    RegistryObject<Requirement<Item>> ELYTRA_REQ = REGISTRY.register("elytra_req", ()-> new Requirement<>(new StatReqType(Stats.ENTITY_KILLED.get(EntityType.ENDER_DRAGON), 5), ()-> Items.ELYTRA));
    RegistryObject<Requirement<Item>> MANA_STEEL_REQ = REGISTRY.register("mana_steel_req", ()-> new Requirement<>(new StatReqType(Stats.ENTITY_KILLED.get(EntityType.ZOMBIE), 50), ModItems.VALKYRIE));
}