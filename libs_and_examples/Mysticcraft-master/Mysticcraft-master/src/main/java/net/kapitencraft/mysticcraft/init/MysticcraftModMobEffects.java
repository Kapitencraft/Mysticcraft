
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.effect.MobEffect;

import net.kapitencraft.mysticcraft.potion.PeaceMobEffect;
import net.kapitencraft.mysticcraft.MysticcraftMod;

public class MysticcraftModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MysticcraftMod.MODID);
	public static final RegistryObject<MobEffect> PEACE = REGISTRY.register("peace", () -> new PeaceMobEffect());
}
