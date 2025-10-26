package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModFluids {
    DeferredRegister<Fluid> REGISTRY = MysticcraftMod.registry(Registries.FLUID);

    Supplier<FlowingFluid> SOURCE_MANA_FLUID = REGISTRY.register("mana_fluid", ()-> new BaseFlowingFluid.Source(FluidProperties.MANA_FLUID_PROPERTIES));
    Supplier<FlowingFluid> FLOWING_MANA_FLUID = REGISTRY.register("flowing_mana_fluid", ()-> new BaseFlowingFluid.Flowing(FluidProperties.MANA_FLUID_PROPERTIES));
}
