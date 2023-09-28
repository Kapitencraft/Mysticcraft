package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.particle.MagicCircleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModParticleTypes {
    DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MysticcraftMod.MOD_ID);
    RegistryObject<ParticleType<?>> FIRE_NORMAL = REGISTRY.register("fire_normal", ()-> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> RED_FLAME = REGISTRY.register("red_flame", () -> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> LIGHT_GREEN_FLAME = REGISTRY.register("light_green_flame", () -> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> DARK_GREEN_FLAME = REGISTRY.register("dark_green_flame", () -> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> LIGHT_BLUE_FLAME = REGISTRY.register("light_blue_flame", () -> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> DARK_BLUE_FLAME = REGISTRY.register("dark_blue_flame", () -> new SimpleParticleType(true));
    RegistryObject<ParticleType<?>> PURPLE_FLAME = REGISTRY.register("purple_flame", () -> new SimpleParticleType(true));
    RegistryObject<MagicCircleParticleType> MAGIC_CIRCLE = REGISTRY.register("magic_circle", ()-> new MagicCircleParticleType(null));
    RegistryObject<SimpleParticleType> DAMAGE_INDICATOR = REGISTRY.register("damage_indicator", ()-> new SimpleParticleType(true));
}