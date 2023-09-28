package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModFluids {
    DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, MysticcraftMod.MOD_ID);

    RegistryObject<FlowingFluid> SOURCE_MANA_FLUID = REGISTRY.register("mana_fluid", ()-> new ForgeFlowingFluid.Source(FluidProperties.MANA_FLUID_PROPERTIES));
    RegistryObject<FlowingFluid> FLOWING_MANA_FLUID = REGISTRY.register("flowing_mana_fluid", ()-> new ForgeFlowingFluid.Flowing(FluidProperties.MANA_FLUID_PROPERTIES));
}
