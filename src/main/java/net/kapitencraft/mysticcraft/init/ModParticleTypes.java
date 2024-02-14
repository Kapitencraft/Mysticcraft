package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.DamageIndicatorParticleOptions;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticleType;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationInfo;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationOptions;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationParameters;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.client.particle.options.FlameParticleOptions;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public interface ModParticleTypes {
    DeferredRegister<ParticleType<?>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.PARTICLE_TYPES);
    RegistryObject<ParticleType<?>> FIRE_NORMAL = REGISTRY.register("fire_normal", ()-> new SimpleParticleType(true));
    RegistryObject<MagicCircleParticleType> MAGIC_CIRCLE = REGISTRY.register("magic_circle", ()-> new MagicCircleParticleType(0));
    RegistryObject<DamageIndicatorParticleOptions> DAMAGE_INDICATOR = REGISTRY.register("damage_indicator", () -> new DamageIndicatorParticleOptions(TextHelper.damageIndicatorCoder("heal"), 1, 1));
    RegistryObject<CircleParticleOptions> CIRCLE = REGISTRY.register("circle_particle", () -> new CircleParticleOptions(new Vector3f(1, 0, 0), 4, 7));
    RegistryObject<ParticleAnimationOptions> ANIMATION = REGISTRY.register("animation", () -> new ParticleAnimationOptions(ParticleTypes.ASH, ParticleAnimationParameters.create(), ParticleAnimationInfo.EMPTY, 1));
    RegistryObject<FlameParticleOptions> FLAME = REGISTRY.register("flame", () -> new FlameParticleOptions(new Vector3f(1, 0, 0)));
}