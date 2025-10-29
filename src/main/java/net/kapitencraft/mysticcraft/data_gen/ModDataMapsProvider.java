package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.potion.ModPotions;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
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
        builder(Skill.FISHING_XP_MAP)
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

        builder(Skill.COMBAT_XP_MAP)
                .add(ResourceLocation.withDefaultNamespace("blaze"), 45, false)
                .add(ResourceLocation.withDefaultNamespace("bogged"), 35, false)
                .add(ResourceLocation.withDefaultNamespace("breeze"), 25, false)
                .add(ResourceLocation.withDefaultNamespace("cave_spider"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("creeper"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("drowned"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("elder_guardian"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("ender_dragon"), 2000, false)
                .add(ResourceLocation.withDefaultNamespace("enderman"), 45, false)
                .add(ResourceLocation.withDefaultNamespace("endermite"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("evoker"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("ghast"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("giant"), 60, false)
                .add(ResourceLocation.withDefaultNamespace("guardian"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("hoglin"), 35, false)
                .add(ResourceLocation.withDefaultNamespace("husk"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("illusioner"), 90, false)
                .add(ResourceLocation.withDefaultNamespace("iron_golem"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("magma_cube"), 25, false)
                .add(ResourceLocation.withDefaultNamespace("phantom"), 30, false)
                .add(ResourceLocation.withDefaultNamespace("piglin"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("piglin_brute"), 75, false)
                .add(ResourceLocation.withDefaultNamespace("pillager"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("ravager"), 65, false)
                .add(ResourceLocation.withDefaultNamespace("shulker"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("silverfish"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("skeleton"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("slime"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("spider"), 8, false)
                .add(ResourceLocation.withDefaultNamespace("stray"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("vex"), 15, false)
                .add(ResourceLocation.withDefaultNamespace("vindicator"), 35, false)
                .add(ResourceLocation.withDefaultNamespace("warden"), 2000, false)
                .add(ResourceLocation.withDefaultNamespace("witch"), 50, false)
                .add(ResourceLocation.withDefaultNamespace("wither"), 2000, false)
                .add(ResourceLocation.withDefaultNamespace("wither_skeleton"), 40, false)
                .add(ResourceLocation.withDefaultNamespace("zoglin"), 35, false)
                .add(ResourceLocation.withDefaultNamespace("zombie"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("zombie_villager"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("zombified_piglin"), 7, false)
                .add(ResourceLocation.withDefaultNamespace("player"), 50, false);

        builder(Skill.FARMING_XP_MAP)
                .add(ResourceLocation.withDefaultNamespace("wheat"), 2, false)
                .add(ResourceLocation.withDefaultNamespace("carrots"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("potatoes"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("beetroots"), 5, false);
        builder(Skill.ENCHANTING_XP_MAP)
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
        builder(Skill.MINING_XP_MAP)
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

        builder(Skill.FORAGING_XP_MAP)
                .add(ResourceLocation.withDefaultNamespace("dark_oak_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("oak_log"), 7, false)
                .add(ResourceLocation.withDefaultNamespace("acacia_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("birch_log"), 10, false)
                .add(ResourceLocation.withDefaultNamespace("jungle_log"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("spruce_log"), 5, false)
                .add(ResourceLocation.withDefaultNamespace("mangrove_log"), 20, false)
                .add(ResourceLocation.withDefaultNamespace("cherry_log"), 10, false)
                .add(ModBlocks.PERIDOT_SYCAMORE_LOG.getId(), 20, false);

        builder(Skill.ALCHEMY_XP_MAP)
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
