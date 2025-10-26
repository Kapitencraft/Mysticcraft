package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModStatTypes {

    DeferredRegister<ResourceLocation> REGISTRY = MysticcraftMod.registry(Registries.CUSTOM_STAT);

    private static Supplier<ResourceLocation> register(String name) {
        return REGISTRY.register(name, () -> MysticcraftMod.res(name));
    }

    Supplier<ResourceLocation> NECRONS_KILLED = register("necrons_killed");
    Supplier<ResourceLocation> STORMS_KILLED = register("storms_killed");
    Supplier<ResourceLocation> MAXORS_KILLED = register("maxors_killed");
    Supplier<ResourceLocation> GOLDORS_KILLED = register("goldors_killed");
}