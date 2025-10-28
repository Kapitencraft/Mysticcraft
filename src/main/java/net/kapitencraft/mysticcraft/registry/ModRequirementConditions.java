package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.kap_lib.requirements.conditions.abstracts.ReqCondition;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.requirement.condition.ClassSelectedRequirementCondition;
import net.kapitencraft.mysticcraft.requirement.condition.SkillLevelRequirementCondition;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModRequirementConditions {
    DeferredRegister<RegistrySerializer<? extends ReqCondition<?>>> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.REQ_CONDITIONS);

    Supplier<RegistrySerializer<ClassSelectedRequirementCondition>> CLASS_SELECTED = REGISTRY.register("class_selected", () -> ClassSelectedRequirementCondition.SERIALIZER);
    Supplier<RegistrySerializer<SkillLevelRequirementCondition>> SKILL_LEVEL = REGISTRY.register("skill_level", () -> SkillLevelRequirementCondition.SERIALIZER);
}
