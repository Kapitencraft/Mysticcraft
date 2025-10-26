package net.kapitencraft.mysticcraft.registry;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class FluidProperties {
    final static BaseFlowingFluid.Properties MANA_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(ModFluidTypes.MANA_FLUID_TYPE, ModFluids.SOURCE_MANA_FLUID, ModFluids.FLOWING_MANA_FLUID).levelDecreasePerBlock(1).slopeFindDistance(8).block(ModBlocks.MANA_FLUID_BLOCK).bucket(ModItems.BUCKET_OF_MANA);
}