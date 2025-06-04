package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModStatTypes {

    DeferredRegister<ResourceLocation> REGISTRY = MysticcraftMod.registry(Registries.CUSTOM_STAT);

    private static RegistryObject<ResourceLocation> register(String name) {
        return REGISTRY.register(name, () -> MysticcraftMod.res(name));
    }

    RegistryObject<ResourceLocation> NECRONS_KILLED = register("necrons_killed");
    RegistryObject<ResourceLocation> STORMS_KILLED = register("storms_killed");
    RegistryObject<ResourceLocation> MAXORS_KILLED = register("maxors_killed");
    RegistryObject<ResourceLocation> GOLDORS_KILLED = register("goldors_killed");
}