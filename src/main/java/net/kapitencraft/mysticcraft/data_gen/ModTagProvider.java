package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public interface ModTagProvider {

    class Items extends ItemTagsProvider {

        public Items(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagsProvider.TagLookup<net.minecraft.world.level.block.Block>> tagLookup, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, registries, tagLookup, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            addArmor(ModItems.CRIMSON_ARMOR);
            addArmor(ModItems.SHADOW_ASSASSIN_ARMOR);
            addArmor(ModItems.FROZEN_BLAZE_ARMOR);
            addArmor(ModItems.TERROR_ARMOR);
            addArmor(ModItems.ENDER_KNIGHT_ARMOR);

            tag(ModTags.Items.DAGGER).add(ModItems.SHADOW_DAGGER.get(), ModItems.DARK_DAGGER.get());
            tag(Tags.Items.TOOLS_BOW).add(ModItems.LONGBOW.get(), ModItems.TALLIN_BOW.get());
            tag(Tags.Items.TOOLS_FISHING_ROD).add(ModItems.LAVA_FISHING_ROD_TEST.get());
            tag(ItemTags.SWORDS).addTags(
                    //ModTags.Items.CLEAVER,
                    ModTags.Items.DAGGER
                    //ModTags.Items.HALBERD,
                    //ModTags.Items.LANCE,
                    //ModTags.Items.SPEAR
            ).add(ModItems.MANA_STEEL_SWORD.get(), ModItems.AOTE.get(), ModItems.AOTV.get());
            tag(Tags.Items.TOOLS_SHIELD).add(ModItems.GOLDEN_SHIELD.get(), ModItems.IRON_SHIELD.get());
            tag(ItemTags.FISHES).add(ModItems.MAGMA_COD.get(), ModItems.BLAZING_SALMON.get());

            tag(ModTags.Items.ENDER_HITTABLE).add(ModItems.TALLIN_BOW.get());

            tag(ModTags.Items.TIER_2_HAMMER).add(ModItems.DIAMOND_HAMMER.get(), ModItems.NETHERITE_HAMMER.get());
            tag(ModTags.Items.TIER_1_HAMMER).addTag(ModTags.Items.TIER_2_HAMMER).add(ModItems.IRON_HAMMER.get());
            tag(ModTags.Items.HAMMER).addTag(ModTags.Items.TIER_1_HAMMER).add(ModItems.STONE_HAMMER.get());
            tag(ItemTags.MINING_ENCHANTABLE).addTags(ModTags.Items.HAMMER);
            tag(ItemTags.DURABILITY_ENCHANTABLE).addTags(ModTags.Items.HAMMER);
            tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTags(ModTags.Items.HAMMER);

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

            tag(ModTags.Items.DRAGON_TEMPTING).add(
                    net.minecraft.world.item.Items.SALMON,
                    net.minecraft.world.item.Items.COOKED_SALMON,
                    net.minecraft.world.item.Items.COD,
                    net.minecraft.world.item.Items.COOKED_COD,
                    net.minecraft.world.item.Items.TROPICAL_FISH
            );
            tag(ModTags.Items.CONTAINER_ENCHANTABLE).add(
                    ModItems.WALLET.value(),
                    ModItems.AMETHYST_QUIVER.value()
            );

            copy(ModTags.Blocks.STRIPPED_LOGS, ModTags.Items.STRIPPED_LOGS);
        }

        private void addArmor(Map<ArmorItem.Type, ? extends DeferredItem<? extends AbstractArmorItem>> armorElements) {
            IntrinsicTagAppender<Item> armorTag = tag(Tags.Items.ARMORS);
            armorElements.values().stream().map(DeferredItem::get).forEach(armorTag::add);
            tag(ItemTags.FOOT_ARMOR).add(armorElements.get(ArmorItem.Type.BOOTS).get());
            tag(ItemTags.LEG_ARMOR).add(armorElements.get(ArmorItem.Type.LEGGINGS).get());
            tag(ItemTags.CHEST_ARMOR).add(armorElements.get(ArmorItem.Type.CHESTPLATE).get());
            tag(ItemTags.HEAD_ARMOR).add(armorElements.get(ArmorItem.Type.HELMET).get());
        }
    }

    class Block extends BlockTagsProvider {

        public Block(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.CRIMSONIUM_ORE.get(),
                    ModBlocks.GEMSTONE_CRYSTAL.get(), ModBlocks.GEMSTONE_BLOCK.get()
            );

            tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.THISTLE.get());

            tag(Tags.Blocks.ORES).add(ModBlocks.CRIMSONIUM_ORE.get(), ModBlocks.GEMSTONE_SEED.get());

            tag(ModTags.Blocks.FARMABLE).addTag(BlockTags.CROPS).add(Blocks.SUGAR_CANE, Blocks.MELON, Blocks.PUMPKIN, Blocks.CHORUS_FLOWER);

            tag(ModTags.Blocks.FORAGEABLE).addTag(BlockTags.LOGS);

            tag(ModTags.Blocks.MINEABLE).addTags(BlockTags.SAND, BlockTags.SNOW, Tags.Blocks.ORES, Tags.Blocks.OBSIDIANS, Tags.Blocks.GRAVELS, Tags.Blocks.NETHERRACKS, Tags.Blocks.STONES, Tags.Blocks.END_STONES, Tags.Blocks.COBBLESTONES);

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

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.GEMSTONE_BLOCK.get(),
                            ModBlocks.GEMSTONE_SEED.get(),
                            ModBlocks.GEMSTONE_CRYSTAL.get(),
                            ModBlocks.REFORGING_ANVIL.get(),
                            ModBlocks.ARTIFICER_TABLE.get(),
                            ModBlocks.MANGATIC_STONE.get()
            );

            tag(BlockTags.BUTTONS).add(ModBlocks.LAPIS_BUTTON.get());
            tag(BlockTags.PRESSURE_PLATES).add(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get());
            tag(BlockTags.SLABS).add(ModBlocks.GOLDEN_SLAB.get());
            tag(BlockTags.STAIRS).add(ModBlocks.GOLDEN_STAIRS.get());
            tag(BlockTags.WALLS).add(ModBlocks.GOLDEN_WALL.get());

            tag(Tags.Blocks.ORES).add(ModBlocks.CRIMSONIUM_ORE.get());

        }
    }

    class Biome extends BiomeTagsProvider {

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

    class Entity extends EntityTypeTagsProvider {

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
            tag(ModTags.Entities.DRAGON_TARGETS).add(
                    EntityType.VILLAGER,
                    EntityType.IRON_GOLEM,
                    EntityType.SLIME,
                    EntityType.ZOMBIE,
                    EntityType.ZOMBIE_VILLAGER,
                    EntityType.HUSK,
                    EntityType.SKELETON,
                    EntityType.WITHER_SKELETON,
                    EntityType.RAVAGER,
                    EntityType.ILLUSIONER,
                    EntityType.VINDICATOR,
                    EntityType.PILLAGER,
                    EntityType.EVOKER,
                    EntityType.STRAY
            );
        }
    }

    class DamageTypes extends DamageTypeTagsProvider {

        public DamageTypes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pLookupProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(DamageTypeTags.IS_FIRE).add(
                    ModDamageTypes.SCORCH
            );
            tag(DamageTypeTags.BYPASSES_COOLDOWN).add(
                    ModDamageTypes.SCORCH
            );
        }
    }
}