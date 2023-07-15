
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import net.kapitencraft.mysticcraft.enchantment.YeetThornsEnchantment;
import net.kapitencraft.mysticcraft.enchantment.YeetEnchantment;
import net.kapitencraft.mysticcraft.enchantment.UltimateWiseEnchantment;
import net.kapitencraft.mysticcraft.enchantment.UltimateFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.TripleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.ThunderlordEnchantment;
import net.kapitencraft.mysticcraft.enchantment.TelekinesisEnchantment;
import net.kapitencraft.mysticcraft.enchantment.StrongFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.QuadripleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.MediumFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.LowFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.GrothEnchantment;
import net.kapitencraft.mysticcraft.enchantment.FirstStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.ElvishMasteryEnchantment;
import net.kapitencraft.mysticcraft.enchantment.DoubleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.CubismEnchantment;
import net.kapitencraft.mysticcraft.enchantment.BigBrainEnchantment;
import net.kapitencraft.mysticcraft.enchantment.AutoSmeltEnchantment;
import net.kapitencraft.mysticcraft.MysticcraftMod;

public class MysticcraftModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MysticcraftMod.MODID);
	public static final RegistryObject<Enchantment> YEET_THORNS = REGISTRY.register("yeet_thorns", () -> new YeetThornsEnchantment());
	public static final RegistryObject<Enchantment> THUNDERLORD = REGISTRY.register("thunderlord", () -> new ThunderlordEnchantment());
	public static final RegistryObject<Enchantment> YEET = REGISTRY.register("yeet", () -> new YeetEnchantment());
	public static final RegistryObject<Enchantment> CUBISM = REGISTRY.register("cubism", () -> new CubismEnchantment());
	public static final RegistryObject<Enchantment> ULTIMATE_WISE = REGISTRY.register("ultimate_wise", () -> new UltimateWiseEnchantment());
	public static final RegistryObject<Enchantment> BIG_BRAIN = REGISTRY.register("big_brain", () -> new BigBrainEnchantment());
	public static final RegistryObject<Enchantment> AUTO_SMELT = REGISTRY.register("auto_smelt", () -> new AutoSmeltEnchantment());
	public static final RegistryObject<Enchantment> TELEKINESIS = REGISTRY.register("telekinesis", () -> new TelekinesisEnchantment());
	public static final RegistryObject<Enchantment> GROTH = REGISTRY.register("groth", () -> new GrothEnchantment());
	public static final RegistryObject<Enchantment> ELVISH_MASTERY = REGISTRY.register("elvish_mastery", () -> new ElvishMasteryEnchantment());
	public static final RegistryObject<Enchantment> FIRST_STRIKE = REGISTRY.register("first_strike", () -> new FirstStrikeEnchantment());
	public static final RegistryObject<Enchantment> DOUBLE_STRIKE = REGISTRY.register("double_strike", () -> new DoubleStrikeEnchantment());
	public static final RegistryObject<Enchantment> TRIPLE_STRIKE = REGISTRY.register("triple_strike", () -> new TripleStrikeEnchantment());
	public static final RegistryObject<Enchantment> QUADRIPLE_STRIKE = REGISTRY.register("quadriple_strike", () -> new QuadripleStrikeEnchantment());
	public static final RegistryObject<Enchantment> LOW_FOCUS = REGISTRY.register("low_focus", () -> new LowFocusEnchantment());
	public static final RegistryObject<Enchantment> MEDIUM_FOCUS = REGISTRY.register("medium_focus", () -> new MediumFocusEnchantment());
	public static final RegistryObject<Enchantment> STRONG_FOCUS = REGISTRY.register("strong_focus", () -> new StrongFocusEnchantment());
	public static final RegistryObject<Enchantment> ULTIMATE_FOCUS = REGISTRY.register("ultimate_focus", () -> new UltimateFocusEnchantment());
}
