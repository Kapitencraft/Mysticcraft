package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.rpg.skill.xp.SkillXpMaps;
import net.kapitencraft.mysticcraft.rpg.skill.xp.combat.SimpleEntityXpProvider;
import net.kapitencraft.mysticcraft.rpg.skill.xp.combat.SizeBasedXpProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapsProvider extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    protected ModDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(SkillXpMaps.FISHING)
                //region lava-junk
                .add(ResourceLocation.withDefaultNamespace("crimson_fungus"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("golden_axe"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("soul_sand"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("bone"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("potion"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("string"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("warped_fungus_on_a_stick"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("warped_fungus"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("blackstone"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("magma_cream"), 40, false)
                //endregion
                //region lava-treasure
                .add(ResourceLocation.withDefaultNamespace("blaze_rod"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("bow"), 200, false)
                .add(ResourceLocation.withDefaultNamespace("enchanted_book"), 200, false)
                .add(ResourceLocation.withDefaultNamespace("netherite_scrap"), 400, false)
                .add(ResourceLocation.withDefaultNamespace("quartz"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("gold_ingot"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("glowstone"), 50, false)
                //endregion
                //region lava-fish
                .add(ModItems.BLAZING_SALMON.getKey(), 50, false)
                .add(ModItems.MAGMA_COD.getKey(), 75, false);
                //endregion

        builder(SkillXpMaps.COMBAT)
                .add(ResourceLocation.withDefaultNamespace("blaze"), new SimpleEntityXpProvider(45), false)
                .add(ResourceLocation.withDefaultNamespace("bogged"), new SimpleEntityXpProvider(35), false)
                .add(ResourceLocation.withDefaultNamespace("breeze"), new SimpleEntityXpProvider(25), false)
                .add(ResourceLocation.withDefaultNamespace("cave_spider"), new SimpleEntityXpProvider(40), false)
                .add(ResourceLocation.withDefaultNamespace("creeper"), new SimpleEntityXpProvider(20), false)
                .add(ResourceLocation.withDefaultNamespace("drowned"), new SimpleEntityXpProvider(10), false)
                .add(ResourceLocation.withDefaultNamespace("elder_guardian"), new SimpleEntityXpProvider(75), false)
                .add(ResourceLocation.withDefaultNamespace("ender_dragon"), new SimpleEntityXpProvider(2000), false)
                .add(ResourceLocation.withDefaultNamespace("enderman"), new SimpleEntityXpProvider(45), false)
                .add(ResourceLocation.withDefaultNamespace("endermite"), new SimpleEntityXpProvider(15), false)
                .add(ResourceLocation.withDefaultNamespace("evoker"), new SimpleEntityXpProvider(75), false)
                .add(ResourceLocation.withDefaultNamespace("ghast"), new SimpleEntityXpProvider(30), false)
                .add(ResourceLocation.withDefaultNamespace("giant"), new SimpleEntityXpProvider(60), false)
                .add(ResourceLocation.withDefaultNamespace("guardian"), new SimpleEntityXpProvider(40), false)
                .add(ResourceLocation.withDefaultNamespace("hoglin"), new SimpleEntityXpProvider(35), false)
                .add(ResourceLocation.withDefaultNamespace("husk"), new SimpleEntityXpProvider(15), false)
                .add(ResourceLocation.withDefaultNamespace("illusioner"), new SimpleEntityXpProvider(90), false)
                .add(ResourceLocation.withDefaultNamespace("iron_golem"), new SimpleEntityXpProvider(50), false)
                .add(ResourceLocation.withDefaultNamespace("magma_cube"), new SizeBasedXpProvider(15, 5), false)
                .add(ResourceLocation.withDefaultNamespace("phantom"), new SimpleEntityXpProvider(30), false)
                .add(ResourceLocation.withDefaultNamespace("piglin"), new SimpleEntityXpProvider(40), false)
                .add(ResourceLocation.withDefaultNamespace("piglin_brute"), new SimpleEntityXpProvider(75), false)
                .add(ResourceLocation.withDefaultNamespace("pillager"), new SimpleEntityXpProvider(20), false)
                .add(ResourceLocation.withDefaultNamespace("ravager"), new SimpleEntityXpProvider(65), false)
                .add(ResourceLocation.withDefaultNamespace("shulker"), new SimpleEntityXpProvider(40), false)
                .add(ResourceLocation.withDefaultNamespace("silverfish"), new SimpleEntityXpProvider(10), false)
                .add(ResourceLocation.withDefaultNamespace("skeleton"), new SimpleEntityXpProvider(15), false)
                .add(ResourceLocation.withDefaultNamespace("slime"), new SizeBasedXpProvider(2, 2), false)
                .add(ResourceLocation.withDefaultNamespace("spider"), new SimpleEntityXpProvider(8), false)
                .add(ResourceLocation.withDefaultNamespace("stray"), new SimpleEntityXpProvider(15), false)
                .add(ResourceLocation.withDefaultNamespace("vex"), new SimpleEntityXpProvider(15), false)
                .add(ResourceLocation.withDefaultNamespace("vindicator"), new SimpleEntityXpProvider(35), false)
                .add(ResourceLocation.withDefaultNamespace("warden"), new SimpleEntityXpProvider(2000), false)
                .add(ResourceLocation.withDefaultNamespace("witch"), new SimpleEntityXpProvider(50), false)
                .add(ResourceLocation.withDefaultNamespace("wither"), new SimpleEntityXpProvider(2000), false)
                .add(ResourceLocation.withDefaultNamespace("wither_skeleton"), new SimpleEntityXpProvider(40), false)
                .add(ResourceLocation.withDefaultNamespace("zoglin"), new SimpleEntityXpProvider(35), false)
                .add(ResourceLocation.withDefaultNamespace("zombie"), new SimpleEntityXpProvider(5), false)
                .add(ResourceLocation.withDefaultNamespace("zombie_villager"), new SimpleEntityXpProvider(5), false)
                .add(ResourceLocation.withDefaultNamespace("zombified_piglin"), new SimpleEntityXpProvider(7), false)
                .add(ResourceLocation.withDefaultNamespace("player"), new SimpleEntityXpProvider(50), false);

        builder(SkillXpMaps.FARMING)
                .add(ResourceLocation.withDefaultNamespace("wheat"), 2, false)
                .add(ResourceLocation.withDefaultNamespace("carrots"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("potatoes"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("beetroots"), 5, false);
        builder(SkillXpMaps.ENCHANTING)
                .add(Enchantments.PROTECTION, 20, false)
                .add(Enchantments.FIRE_PROTECTION, 20, false)
                .add(Enchantments.FEATHER_FALLING, 25, false)
                .add(Enchantments.BLAST_PROTECTION, 20, false)
                .add(Enchantments.PROJECTILE_PROTECTION, 20, false)
                .add(Enchantments.RESPIRATION, 30, false)
                .add(Enchantments.AQUA_AFFINITY, 40, false)
                .add(Enchantments.THORNS, 50, false)
                .add(Enchantments.DEPTH_STRIDER, 30, false)
                .add(Enchantments.SHARPNESS, 20, false)
                .add(Enchantments.SMITE, 20, false)
                .add(Enchantments.BANE_OF_ARTHROPODS, 20, false)
                .add(Enchantments.KNOCKBACK, 10, false)
                .add(Enchantments.FIRE_ASPECT, 50, false)
                .add(Enchantments.LOOTING, 50, false)
                .add(Enchantments.SWEEPING_EDGE, 40, false)
                .add(Enchantments.EFFICIENCY, 10, false)
                .add(Enchantments.SILK_TOUCH, 30, false)
                .add(Enchantments.UNBREAKING, 5, false)
                .add(Enchantments.FORTUNE, 35, false)
                .add(Enchantments.POWER, 10, false)
                .add(Enchantments.PUNCH, 30, false)
                .add(Enchantments.FLAME, 50, false)
                .add(Enchantments.INFINITY, 40, false)
                .add(Enchantments.LUCK_OF_THE_SEA, 20, false)
                .add(Enchantments.LURE, 20, false)
                .add(Enchantments.LOYALTY, 30, false)
                .add(Enchantments.IMPALING, 20, false)
                .add(Enchantments.RIPTIDE, 30, false)
                .add(Enchantments.CHANNELING, 35, false)
                .add(Enchantments.MULTISHOT, 30, false)
                .add(Enchantments.QUICK_CHARGE, 20, false)
                .add(Enchantments.PIERCING, 30, false)
                .add(Enchantments.DENSITY, 30, false)
                .add(Enchantments.BREACH, 30, false);
        builder(SkillXpMaps.MINING)
                .add(BlockTags.STONE_ORE_REPLACEABLES, 5, false)
                .add(ResourceLocation.withDefaultNamespace("tuff"), 7, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate"), 8, false)
                .add(ResourceLocation.withDefaultNamespace("netherrack"), 1, false)
                .add(ResourceLocation.withDefaultNamespace("grass_block"), 2, false)
                .add(ResourceLocation.withDefaultNamespace("dirt"), 1, false)
                .add(ResourceLocation.withDefaultNamespace("podzol"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("cobblestone"), 7, false)
                .add(ResourceLocation.withDefaultNamespace("bedrock"), 10000, false) //if you manage to do that, you should get rewarded
                .add(ResourceLocation.withDefaultNamespace("reinforced_deepslate"), 10000, false)
                .add(ResourceLocation.withDefaultNamespace("sand"), 3, false)
                .add(ResourceLocation.withDefaultNamespace("gravel"), 3, false)
                .add(ResourceLocation.withDefaultNamespace("red_sand"), 3, false)
                .add(ResourceLocation.withDefaultNamespace("coal_ore"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_coal_ore"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("copper_ore"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_copper_ore"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("iron_ore"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_iron_ore"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("raw_iron_block"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("lapis_ore"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_lapis_ore"), 22, false)
                .add(ResourceLocation.withDefaultNamespace("redstone_ore"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_redstone_ore"), 25, false)
                .add(ResourceLocation.withDefaultNamespace("gold_ore"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_gold_ore"), 35, false)
                .add(ResourceLocation.withDefaultNamespace("diamond_ore"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("deepslate_diamond_ore"), 60, false)
                .add(ResourceLocation.withDefaultNamespace("nether_gold_ore"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("ancient_debris"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("blackstone"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("basalt"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("amethyst_block"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("calcite"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("amethyst_cluster"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("sculk"), 2, false)
                .add(ResourceLocation.withDefaultNamespace("sculk_catalyst"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("sculk_shrieker"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("pointed_dripstone"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("dripstone_block"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("mud"), 5, false)
                .add(ModBlocks.GEMSTONE_BLOCK.getId(), 40, false)
                .add(ModBlocks.CRIMSONIUM_ORE.getId(), 120, false);

        builder(SkillXpMaps.FORAGING)
                .add(ResourceLocation.withDefaultNamespace("dark_oak_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("oak_log"), 7, false)
                .add(ResourceLocation.withDefaultNamespace("acacia_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("birch_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("jungle_log"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("spruce_log"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("mangrove_log"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("cherry_log"), 10, false)
                .add(ModBlocks.PERIDOT_SYCAMORE_LOG.getId(), 20, false);

        builder(SkillXpMaps.ALCHEMY)
                .add(ResourceLocation.withDefaultNamespace("night_vision"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("long_night_vision"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("invisibility"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("long_invisibility"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("leaping"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("long_leaping"), 22, false)
                .add(ResourceLocation.withDefaultNamespace("strong_leaping"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("fire_resistance"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("long_fire_resistance"), 45, false)
                .add(ResourceLocation.withDefaultNamespace("swiftness"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("long_swiftness"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("strong_swiftness"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("slowness"), 25, false)
                .add(ResourceLocation.withDefaultNamespace("long_slowness"), 37, false)
                .add(ResourceLocation.withDefaultNamespace("strong_slowness"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("turtle_master"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("long_turtle_master"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("strong_turtle_master"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("water_breathing"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("long_water_breathing"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("healing"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("strong_healing"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("harming"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("strong_harming"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("poison"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("long_poison"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("strong_poison"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("regeneration"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("long_regeneration"), 45, false)
                .add(ResourceLocation.withDefaultNamespace("strong_regeneration"), 60, false)
                .add(ResourceLocation.withDefaultNamespace("strength"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("long_strength"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("strong_strenght"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("weakness"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("long_weakness"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("luck"), 100, false)
                .add(ResourceLocation.withDefaultNamespace("slow_falling"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("long_slow_falling"), 45, false)
                .add(ResourceLocation.withDefaultNamespace("wind_charged"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("weaving"), 25, false)
                .add(ResourceLocation.withDefaultNamespace("oozing"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("infested"), 20, false)
                .add(ModPotions.STUN.getKey(), 100, false)
                .add(ModPotions.LONG_STUN.getKey(), 150, false)
                .add(ModPotions.DISPLACEMENT.getKey(), 200, false);
    }
}
