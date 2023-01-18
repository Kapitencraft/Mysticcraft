package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, MysticcraftMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_MANA_FLUID = REGISTRY.register("mana_fluid", ()-> new ForgeFlowingFluid.Source(ModFluids.MANA_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MANA_FLUID = REGISTRY.register("flowing_mana_fluid", ()-> new ForgeFlowingFluid.Flowing(ModFluids.MANA_FLUID_PROPERTIES));
    private static final ForgeFlowingFluid.Properties MANA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(ModFluidTypes.MANA_FLUID_TYPE, SOURCE_MANA_FLUID, FLOWING_MANA_FLUID).levelDecreasePerBlock(1).slopeFindDistance(8).block(ModBlocks.MANA_FLUID_BLOCK).bucket(ModItems.BUCKET_OF_MANA);
}
