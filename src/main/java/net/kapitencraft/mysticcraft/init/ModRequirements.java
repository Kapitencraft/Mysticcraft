package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.requirements.type.CustomStatReqType;
import net.kapitencraft.mysticcraft.requirements.type.StatReqType;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModRequirements {
    DeferredRegister<Requirement<?>> REGISTRY = MysticcraftMod.makeRegistry(ModRegistryKeys.REQUIREMENTS);

    RegistryObject<Requirement<Item>> ELYTRA_REQ = REGISTRY.register("elytra_req", ()-> new Requirement<>(new StatReqType(Stats.ENTITY_KILLED.get(EntityType.ENDER_DRAGON), 5), ()-> Items.ELYTRA));
    RegistryObject<Requirement<Item>> HYPERION_REQ = REGISTRY.register("hyperion_req", ()-> new Requirement<>(new CustomStatReqType(Stats.CUSTOM.get(ModStatTypes.STORMS_KILLED.get()), 10, "stat_req.storm"), ModItems.HYPERION));
    RegistryObject<Requirement<Item>> VALKYRIE_REQ = REGISTRY.register("valkyrie_req", ()-> new Requirement<>(new CustomStatReqType(Stats.CUSTOM.get(ModStatTypes.NECRONS_KILLED.get()), 10, "stat_req.necron"), ModItems.VALKYRIE));
    RegistryObject<Requirement<Item>> SCYLLA_REQ = REGISTRY.register("scylla_req", ()-> new Requirement<>(new CustomStatReqType(Stats.CUSTOM.get(ModStatTypes.MAXORS_KILLED.get()), 10, "stat_req.maxor"), ModItems.SCYLLA));
    RegistryObject<Requirement<Item>> ASTREA_REQ = REGISTRY.register("astrea_req", ()-> new Requirement<>(new CustomStatReqType(Stats.CUSTOM.get(ModStatTypes.GOLDORS_KILLED.get()), 10, "stat_req.goldor"), ModItems.ASTREA));
}