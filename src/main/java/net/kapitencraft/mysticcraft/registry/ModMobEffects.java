package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.mob_effects.BlazingEffect;
import net.kapitencraft.mysticcraft.mob_effects.DisplacementEffect;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessEffect;
import net.kapitencraft.mysticcraft.mob_effects.VulnerabilityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModMobEffects {
    DeferredRegister<MobEffect> REGISTRY = MysticcraftMod.registry(ForgeRegistries.MOB_EFFECTS);
    RegistryObject<MobEffect> VULNERABILITY = REGISTRY.register("vulnerability", VulnerabilityEffect::new);
    RegistryObject<MobEffect> NUMBNESS = REGISTRY.register("numbness", NumbnessEffect::new);
    RegistryObject<MobEffect> BLAZING = REGISTRY.register("blazing", BlazingEffect::new);
    RegistryObject<MobEffect> DISPLACEMENT = REGISTRY.register("displacement", DisplacementEffect::new);
}