
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;

import net.kapitencraft.mysticcraft.world.features.ores.TitaniumOreFeature;
import net.kapitencraft.mysticcraft.world.features.ores.RubyOreFeature;
import net.kapitencraft.mysticcraft.world.features.ores.MithrilOreFeature;
import net.kapitencraft.mysticcraft.world.features.ores.CopperOreFeature;
import net.kapitencraft.mysticcraft.world.features.VoidGloomCometeFeature;
import net.kapitencraft.mysticcraft.world.features.FlyingCastleFeature;
import net.kapitencraft.mysticcraft.MysticcraftMod;

import java.util.function.Supplier;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class MysticcraftModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, MysticcraftMod.MODID);
	private static final List<FeatureRegistration> FEATURE_REGISTRATIONS = new ArrayList<>();
	public static final RegistryObject<Feature<?>> MITHRIL_ORE = register("mithril_ore", MithrilOreFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.UNDERGROUND_ORES, MithrilOreFeature.GENERATE_BIOMES, MithrilOreFeature::placedFeature));
	public static final RegistryObject<Feature<?>> TITANIUM_ORE = register("titanium_ore", TitaniumOreFeature::feature, new FeatureRegistration(
			GenerationStep.Decoration.UNDERGROUND_ORES, TitaniumOreFeature.GENERATE_BIOMES, TitaniumOreFeature::placedFeature));
	public static final RegistryObject<Feature<?>> COPPER_ORE = register("copper_ore", CopperOreFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.UNDERGROUND_ORES, CopperOreFeature.GENERATE_BIOMES, CopperOreFeature::placedFeature));
	public static final RegistryObject<Feature<?>> RUBY_ORE = register("ruby_ore", RubyOreFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.UNDERGROUND_ORES, RubyOreFeature.GENERATE_BIOMES, RubyOreFeature::placedFeature));
	public static final RegistryObject<Feature<?>> FLYING_CASTLE = register("flying_castle", FlyingCastleFeature::feature, new FeatureRegistration(
			GenerationStep.Decoration.RAW_GENERATION, FlyingCastleFeature.GENERATE_BIOMES, FlyingCastleFeature::placedFeature));
	public static final RegistryObject<Feature<?>> VOID_GLOOM_COMETE = register("void_gloom_comete", VoidGloomCometeFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.RAW_GENERATION, VoidGloomCometeFeature.GENERATE_BIOMES,
					VoidGloomCometeFeature::placedFeature));

	private static RegistryObject<Feature<?>> register(String registryname, Supplier<Feature<?>> feature, FeatureRegistration featureRegistration) {
		FEATURE_REGISTRATIONS.add(featureRegistration);
		return REGISTRY.register(registryname, feature);
	}

	@SubscribeEvent
	public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
		for (FeatureRegistration registration : FEATURE_REGISTRATIONS) {
			if (registration.biomes() == null || registration.biomes().contains(event.getName()))
				event.getGeneration().getFeatures(registration.stage()).add(registration.placedFeature().get());
		}
	}

	private static record FeatureRegistration(GenerationStep.Decoration stage, Set<ResourceLocation> biomes,
			Supplier<Holder<PlacedFeature>> placedFeature) {
	}
}
