package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class ModTagProvider {

    public static class Item extends ItemTagsProvider {

        public Item(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagsProvider.TagLookup<net.minecraft.world.level.block.Block>> tagLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, registries, tagLookup, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            addArmors(
                    ModItems.CRIMSON_ARMOR,
                    ModItems.SHADOW_ASSASSIN_ARMOR,
                    ModItems.FROZEN_BLAZE_ARMOR,
                    ModItems.TERROR_ARMOR,
                    ModItems.ENDER_KNIGHT_ARMOR
            );
            tag(ModTags.Items.DAGGER).add(ModItems.SHADOW_DAGGER.get(), ModItems.DARK_DAGGER.get());
            tag(Tags.Items.TOOLS_BOWS).add(ModItems.LONGBOW.get(), ModItems.TALLIN_BOW.get());
            tag(Tags.Items.TOOLS_FISHING_RODS).add(ModItems.LAVA_FISHING_ROD_TEST.get());
            tag(ItemTags.SWORDS).addTags(
                    //ModTags.Items.CLEAVER,
                    ModTags.Items.DAGGER
                    //ModTags.Items.HALBERD,
                    //ModTags.Items.LANCE,
                    //ModTags.Items.SPEAR
            ).add(ModItems.MANA_STEEL_SWORD.get(), ModItems.AOTE.get(), ModItems.AOTV.get());
            tag(Tags.Items.TOOLS_SHIELDS).add(ModItems.GOLDEN_SHIELD.get(), ModItems.IRON_SHIELD.get());
            tag(ItemTags.FISHES).add(ModItems.MAGMA_COD.get(), ModItems.BLAZING_SALMON.get());

            tag(ModTags.Items.ENDER_HITTABLE).add(ModItems.TALLIN_BOW.get());

            tag(ModTags.Items.TIER_2_HAMMER).add(ModItems.DIAMOND_HAMMER.get(), ModItems.NETHERITE_HAMMER.get());
            tag(ModTags.Items.TIER_1_HAMMER).addTag(ModTags.Items.TIER_2_HAMMER).add(ModItems.IRON_HAMMER.get());
            tag(ModTags.Items.HAMMER).addTag(ModTags.Items.TIER_1_HAMMER).add(ModItems.STONE_HAMMER.get());

            tag(ModTags.Items.CATALYST).add(
                    ModItems.SPELL_SCROLL.get(),
                    ModItems.AOTE.get(), ModItems.AOTV.get(),
                    ModItems.ASTREA.get(), ModItems.VALKYRIE.get(), ModItems.HYPERION.get(), ModItems.SCYLLA.get(), ModItems.NECRON_SWORD.get(),
                    ModItems.HEATED_SCYTHE.get(), ModItems.FIERY_SCYTHE.get(), ModItems.BURNING_SCYTHE.get(), ModItems.INFERNAL_SCYTHE.get(),
                    ModItems.SHADOW_DAGGER.get(),
                    ModItems.FIRE_LANCE.get(),
                    ModItems.VOID_STAFF.get(), ModItems.THE_STAFF_DESTRUCTION.get()
            );
            tag(ModTags.Items.UPGRADE).add(
                    ModItems.PARALLEL_PROCESSING_UPGRADE.get(),
                    ModItems.SPEED_UPGRADE.get()
            );

            copy(ModTags.Blocks.STRIPPED_LOGS, ModTags.Items.STRIPPED_LOGS);
        }

        @SafeVarargs
        private void addArmors(Map<ArmorItem.Type, ? extends RegistryObject<? extends AbstractArmorItem>>... armorElements) {
            collect(Arrays.stream(armorElements).map(Map::values).flatMap(Collection::stream), tag(Tags.Items.ARMORS));
            collect(Arrays.stream(armorElements).map(map -> map.get(ArmorItem.Type.BOOTS)), tag(Tags.Items.ARMORS_BOOTS));
            collect(Arrays.stream(armorElements).map(map -> map.get(ArmorItem.Type.LEGGINGS)), tag(Tags.Items.ARMORS_LEGGINGS));
            collect(Arrays.stream(armorElements).map(map -> map.get(ArmorItem.Type.CHESTPLATE)), tag(Tags.Items.ARMORS_CHESTPLATES));
            collect(Arrays.stream(armorElements).map(map -> map.get(ArmorItem.Type.HELMET)), tag(Tags.Items.ARMORS_HELMETS));
        }

        private void collect(Stream<? extends RegistryObject<? extends AbstractArmorItem>> stream, IntrinsicTagAppender<net.minecraft.world.item.Item> appender) {
            stream.map(RegistryObject::get).forEach(appender::add);
        }
    }

    public static class Block extends BlockTagsProvider {

        public Block(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.CRIMSONIUM_ORE.get(),
                    ModBlocks.GEMSTONE_CRYSTAL.get(), ModBlocks.GEMSTONE_BLOCK.get()
            );

            tag(Tags.Blocks.ORES).add(ModBlocks.CRIMSONIUM_ORE.get(), ModBlocks.GEMSTONE_SEED.get());

            tag(ModTags.Blocks.FARMABLE).addTag(BlockTags.CROPS).add(Blocks.SUGAR_CANE, Blocks.MELON, Blocks.PUMPKIN, Blocks.CHORUS_FLOWER);

            tag(ModTags.Blocks.FORAGEABLE).addTag(BlockTags.LOGS);

            tag(ModTags.Blocks.MINEABLE).addTags(Tags.Blocks.SAND, BlockTags.SNOW, Tags.Blocks.ORES, Tags.Blocks.OBSIDIAN, Tags.Blocks.GRAVEL, Tags.Blocks.NETHERRACK, Tags.Blocks.STONE, Tags.Blocks.END_STONES, Tags.Blocks.COBBLESTONE);

            tag(BlockTags.LOGS).add(ModBlocks.PERIDOT_SYCAMORE_LOG.get());

            tag(ModTags.Blocks.STRIPPED_LOGS).add(
                    Blocks.STRIPPED_OAK_LOG,
                    Blocks.STRIPPED_ACACIA_LOG,
                    Blocks.STRIPPED_JUNGLE_LOG,
                    Blocks.STRIPPED_DARK_OAK_LOG,
                    Blocks.STRIPPED_SPRUCE_LOG,
                    Blocks.STRIPPED_OAK_WOOD,
                    Blocks.STRIPPED_ACACIA_WOOD,
                    Blocks.STRIPPED_JUNGLE_WOOD,
                    Blocks.STRIPPED_DARK_OAK_WOOD,
                    Blocks.STRIPPED_SPRUCE_WOOD,
                    ModBlocks.STRIPPED_PERIDOT_SYCAMORE_LOG.get()
            );
        }
    }

    public static class Biome extends BiomeTagsProvider {

        public Biome(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(ModTags.Biomes.GEMSTONE_SPAWN)
                    .addTags(BiomeTags.IS_OVERWORLD, Tags.Biomes.IS_MOUNTAIN)
                    .add(Biomes.END_HIGHLANDS, Biomes.BASALT_DELTAS);
        }
    }

    public static class Entity extends EntityTypeTagsProvider {

        public Entity(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(ModTags.Entities.NETHER_MOBS).add(
                    EntityType.BLAZE,
                    EntityType.ZOMBIFIED_PIGLIN,
                    EntityType.WITHER_SKELETON,
                    EntityType.PIGLIN,
                    EntityType.HOGLIN,
                    ModEntityTypes.FROZEN_BLAZE.get()
            );
        }
    }
}