package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.mob_effects.BlazingEffect;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessMobEffect;
import net.kapitencraft.mysticcraft.mob_effects.VulnerabilityMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModMobEffects {
    DeferredRegister<MobEffect> REGISTRY = MysticcraftMod.registry(ForgeRegistries.MOB_EFFECTS);
    RegistryObject<MobEffect> VULNERABILITY = REGISTRY.register("vulnerability", VulnerabilityMobEffect::new);
    RegistryObject<MobEffect> NUMBNESS = REGISTRY.register("numbness", NumbnessMobEffect::new);
    RegistryObject<MobEffect> BLAZING = REGISTRY.register("blazing", BlazingEffect::new);
}