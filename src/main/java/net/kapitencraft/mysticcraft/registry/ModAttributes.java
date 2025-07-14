package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModAttributes {
    DeferredRegister<Attribute> REGISTRY = MysticcraftMod.registry(ForgeRegistries.ATTRIBUTES);

    RegistryObject<RangedAttribute> CAST_DURATION = REGISTRY.register("cast_duration", () -> new RangedAttribute("cast_duration", 0, -100, 1000));
}
