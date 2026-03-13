package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.core.util.Color;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticleType;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModParticleTypes {
    DeferredRegister<ParticleType<?>> REGISTRY = MysticcraftMod.registry(Registries.PARTICLE_TYPE);
    Supplier<SimpleParticleType> FIRE_NORMAL = REGISTRY.register("fire_normal", ()-> new SimpleParticleType(true));
    Supplier<SimpleParticleType> SHADOW_SWEEP = REGISTRY.register("shadow_sweep", ()-> new SimpleParticleType(true));
    Supplier<MagicCircleParticleType> MAGIC_CIRCLE = REGISTRY.register("magic_circle", ()-> new MagicCircleParticleType(0));
    Supplier<CircleParticleOptions> CIRCLE = REGISTRY.register("circle_particle", () -> new CircleParticleOptions(new Color(1, 0, 0, 1), 4, 7));
    Supplier<FlameParticleOptions> FLAME = REGISTRY.register("flame", () -> new FlameParticleOptions(new Color(1, 0, 0, 1)));
}