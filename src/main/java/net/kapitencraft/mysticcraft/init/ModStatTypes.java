package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;

public interface ModStatTypes {

    DeferredRegister<ResourceLocation> REGISTRY = MysticcraftMod.makeRegistry(Registries.CUSTOM_STAT);

}
