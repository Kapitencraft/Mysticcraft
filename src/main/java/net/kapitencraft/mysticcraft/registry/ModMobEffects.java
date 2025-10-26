package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.mob_effects.BlazingEffect;
import net.kapitencraft.mysticcraft.mob_effects.DisplacementEffect;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessEffect;
import net.kapitencraft.mysticcraft.mob_effects.VulnerabilityEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModMobEffects {
    DeferredRegister<MobEffect> REGISTRY = MysticcraftMod.registry(Registries.MOB_EFFECT);
    Holder<MobEffect> VULNERABILITY = REGISTRY.register("vulnerability", VulnerabilityEffect::new);
    Holder<MobEffect> NUMBNESS = REGISTRY.register("numbness", NumbnessEffect::new);
    Holder<MobEffect> BLAZING = REGISTRY.register("blazing", BlazingEffect::new);
    Holder<MobEffect> DISPLACEMENT = REGISTRY.register("displacement", DisplacementEffect::new);
}