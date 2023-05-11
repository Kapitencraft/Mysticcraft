package net.kapitencraft.mysticcraft.item;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluid;

public class LavaFishingRod extends ModFishingRod {
    public LavaFishingRod(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }

    @Override
    public TagKey<Fluid> getFluidType() {
        return FluidTags.LAVA;
    }
}
