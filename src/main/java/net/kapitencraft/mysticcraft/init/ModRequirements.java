package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.requirements.type.RequirementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRequirements {
    DeferredRegister<Requirement> REGISTRY = MysticcraftMod.makeCustomRegistry(ModRegistryKeys.REQUIREMENTS, RegistryBuilder::new);

    Requirement NONE = new Requirement(new RequirementType(value -> 1, 10), ModItems.ASTREA);
}