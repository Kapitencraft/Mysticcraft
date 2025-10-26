package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public interface ModFluidTypes {

    DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MysticcraftMod.MOD_ID);

    Supplier<FluidType> MANA_FLUID_TYPE = register("mana_fluid", FluidType.Properties.create().supportsBoating(true).canConvertToSource(true).rarity(Rarity.EPIC).motionScale(0.5));

    static Supplier<FluidType> register(String name, FluidType.Properties properties) {
        return REGISTRY.register(name, ()-> new FluidType(properties));
    }
}
