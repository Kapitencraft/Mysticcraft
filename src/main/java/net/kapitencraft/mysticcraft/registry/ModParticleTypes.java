package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticleType;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModParticleTypes {
    DeferredRegister<ParticleType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.PARTICLE_TYPES);
    RegistryObject<SimpleParticleType> FIRE_NORMAL = REGISTRY.register("fire_normal", ()-> new SimpleParticleType(true));
    RegistryObject<SimpleParticleType> SHADOW_SWEEP = REGISTRY.register("shadow_sweep", ()-> new SimpleParticleType(true));
    RegistryObject<MagicCircleParticleType> MAGIC_CIRCLE = REGISTRY.register("magic_circle", ()-> new MagicCircleParticleType(0));
    RegistryObject<CircleParticleOptions> CIRCLE = REGISTRY.register("circle_particle", () -> new CircleParticleOptions(new Color(1, 0, 0, 1), 4, 7));
    RegistryObject<FlameParticleOptions> FLAME = REGISTRY.register("flame", () -> new FlameParticleOptions(new Color(1, 0, 0, 1)));
}