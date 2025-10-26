package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public interface ModMemoryModuleTypes {
    DeferredRegister<MemoryModuleType<?>> REGISTRY = MysticcraftMod.registry(Registries.MEMORY_MODULE_TYPE);

    Supplier<MemoryModuleType<Unit>> DRAGON_FIRE_BREATH_COOLDOWN = REGISTRY.register("dragon/fire_breath_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));
}