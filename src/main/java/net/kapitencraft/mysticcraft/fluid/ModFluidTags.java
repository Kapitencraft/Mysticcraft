package net.kapitencraft.mysticcraft.fluid;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.Objects;

public class ModFluidTags {
    public static final TagKey<Fluid> MANA_FLUID = create("mana_fluid");

    private static TagKey<Fluid> create(String name) {
        return TagKey.create(Registries.FLUID, MysticcraftMod.res(name));
    }

    public static @Nullable TagKey<Fluid> getByName(String name) {
        if (Objects.equals(name, MANA_FLUID.location().toString())) {
            return MANA_FLUID;
        } else if (Objects.equals(name, FluidTags.WATER.location().toString())) {
            return FluidTags.WATER;
        } else if (Objects.equals(name, FluidTags.LAVA.location().toString())) {
            return FluidTags.LAVA;
        } else {
            return null;
        }
    }
}