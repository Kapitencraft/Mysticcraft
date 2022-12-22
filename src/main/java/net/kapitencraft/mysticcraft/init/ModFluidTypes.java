package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.fluid.ModFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    public static final ResourceLocation MANA_FLUID_OVERLAY = new ResourceLocation(MysticcraftMod.MOD_ID, "block/mana_fluid");

    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MysticcraftMod.MOD_ID);

    public static final RegistryObject<FluidType> MANA_FLUID_TYPE = register("mana_fluid", FluidType.Properties.create().supportsBoating(true).canConvertToSource(true).rarity(Rarity.EPIC).motionScale(0.5));


    public static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return REGISTRY.register(name, ()-> new ModFluidType(properties, WATER_STILL, WATER_FLOW, MANA_FLUID_OVERLAY, 0xA100e2ff, new Vector3f()));
    }
}
