package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;

public interface ModAttributes {
    DeferredRegister<Attribute> REGISTRY = MysticcraftMod.registry(Registries.ATTRIBUTE);

    Holder<Attribute> CAST_DURATION = REGISTRY.register("cast_duration", () -> new RangedAttribute("cast_duration", 0, -100, 1000));
    EnumMap<Skill, Holder<Attribute>> XP_BOOSTS = registerXpBoosts();

    private static EnumMap<Skill, Holder<Attribute>> registerXpBoosts() {
        EnumMap<Skill, Holder<Attribute>> map = new EnumMap<>(Skill.class);
        for (Skill value : Skill.values()) {
            String name = value.getSerializedName() + "_xp_boost";
            map.put(value, REGISTRY.register(name, () -> new RangedAttribute(name, 100, 0, 5000))); //x50 is maximum
        }
        return map;
    }
}
