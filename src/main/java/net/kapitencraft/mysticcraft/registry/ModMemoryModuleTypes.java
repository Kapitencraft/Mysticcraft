package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public interface ModMemoryModuleTypes {
    DeferredRegister<MemoryModuleType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.MEMORY_MODULE_TYPES);

    RegistryObject<MemoryModuleType<Unit>> DRAGON_FIRE_BREATH_COOLDOWN = REGISTRY.register("dragon/fire_breath_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));
}