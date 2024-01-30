package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModStatTypes {

    DeferredRegister<ResourceLocation> REGISTRY = MysticcraftMod.makeRegistry(Registries.CUSTOM_STAT);

    RegistryObject<ResourceLocation> NECRONS_KILLED = REGISTRY.register("necrons_killed", ()-> new ResourceLocation("mysticcraft:necrons_killed"));
    RegistryObject<ResourceLocation> STORMS_KILLED = REGISTRY.register("storms_killed", ()-> new ResourceLocation("mysticcraft:storms_killed"));
    RegistryObject<ResourceLocation> MAXORS_KILLED = REGISTRY.register("maxors_killed", ()-> new ResourceLocation("mysticcraft:maxors_killed"));
    RegistryObject<ResourceLocation> GOLDORS_KILLED = REGISTRY.register("goldors_killed", ()-> new ResourceLocation("mysticcraft:goldors_killed"));
}