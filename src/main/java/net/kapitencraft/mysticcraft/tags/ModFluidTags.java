package net.kapitencraft.mysticcraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.Objects;

public interface ModFluidTags {
    TagKey<Fluid> MANA_FLUID = Tags.makeKey(Registries.FLUID, "mysticcraft:mana_fluid");

    static @Nullable TagKey<Fluid> getByName(String name) {
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