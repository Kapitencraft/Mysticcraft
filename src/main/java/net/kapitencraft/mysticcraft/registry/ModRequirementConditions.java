package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.requirement.conditions.abstracts.ReqCondition;
import net.kapitencraft.kap_lib.requirement.registry.RequirementRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.requirement.condition.ClassSelectedRequirementCondition;
import net.kapitencraft.mysticcraft.requirement.condition.SkillLevelRequirementCondition;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModRequirementConditions {
    DeferredRegister<RegistrySerializer<? extends ReqCondition<?>>> REGISTRY = MysticcraftMod.registry(RequirementRegistries.Keys.REQ_CONDITIONS);

    Supplier<RegistrySerializer<ClassSelectedRequirementCondition>> CLASS_SELECTED = REGISTRY.register("class_selected", () -> ClassSelectedRequirementCondition.SERIALIZER);
    Supplier<RegistrySerializer<SkillLevelRequirementCondition>> SKILL_LEVEL = REGISTRY.register("skill_level", () -> SkillLevelRequirementCondition.SERIALIZER);
}
