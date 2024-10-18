package net.kapitencraft.mysticcraft.data.gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.combat.armor.ModArmorItem;
import net.kapitencraft.mysticcraft.tags.ModBlockTags;
import net.kapitencraft.mysticcraft.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModTagProvider implements DataProvider {
    private final ItemProvider itemProvider;
    private final BlockProvider blockProvider;

    public ModTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        this.blockProvider = new BlockProvider(output, future, existingFileHelper);
        this.itemProvider = new ItemProvider(output, future, this.blockProvider, existingFileHelper);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        return CompletableFuture.allOf(
                this.blockProvider.run(pOutput),
                this.itemProvider.run(pOutput)
        );
    }

    @Override
    public String getName() {
        return "ModTagsProvider";
    }

    private static class ItemProvider extends ItemTagsProvider {

        public ItemProvider(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, TagsProvider<Block> p_256467_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_255871_, p_256035_, p_256467_, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            addArmors(List.of(ModItems.CRIMSON_ARMOR, ModItems.SHADOW_ASSASSIN_ARMOR, ModItems.FROZEN_BLAZE_ARMOR, ModItems.TERROR_ARMOR, ModItems.ENDER_KNIGHT_ARMOR));
            tag(ModItemTags.SWORD).add(ModItems.MANA_STEEL_SWORD.get());
            tag(ModItemTags.DAGGER).add(ModItems.SHADOW_DAGGER.get(), ModItems.DARK_DAGGER.get());
            tag(Tags.Items.TOOLS_BOWS).add(ModItems.LONGBOW.get(), ModItems.TALLIN_BOW.get());
            tag(Tags.Items.TOOLS_FISHING_RODS).add(ModItems.LAVA_FISHING_ROD_TEST.get());
            tag(Tags.Items.TOOLS_SWORDS).addTags(
                    //ModItemTags.CLEAVER,
                    ModItemTags.SWORD,
                    ModItemTags.DAGGER
                    //ModItemTags.HALBERD,
                    //ModItemTags.LANCE,
                    //ModItemTags.SPEAR
            );
            tag(Tags.Items.TOOLS_SHIELDS).add(ModItems.GOLDEN_SHIELD.get(), ModItems.IRON_SHIELD.get());
            tag(ItemTags.FISHES).add(ModItems.MAGMA_COD.get(), ModItems.BLAZING_SALMON.get());

            tag(ModItemTags.ENDER_HITTABLE).add(ModItems.TALLIN_BOW.get());

            tag(ModItemTags.TIER_2_HAMMER).add(ModItems.DIAMOND_HAMMER.get());
            tag(ModItemTags.TIER_1_HAMMER).addTag(ModItemTags.TIER_2_HAMMER).add(ModItems.IRON_HAMMER.get());
            tag(ModItemTags.DEFAULT_HAMMER).addTag(ModItemTags.TIER_1_HAMMER).add(ModItems.STONE_HAMMER.get());
        }

        private void addArmors(List<HashMap<EquipmentSlot, ? extends RegistryObject<? extends ModArmorItem>>> armorElements) {
            collect(armorElements.stream().map(HashMap::values).flatMap(Collection::stream), tag(Tags.Items.ARMORS));
            collect(armorElements.stream().map(map -> map.get(EquipmentSlot.FEET)), tag(Tags.Items.ARMORS_BOOTS));
            collect(armorElements.stream().map(map -> map.get(EquipmentSlot.LEGS)), tag(Tags.Items.ARMORS_LEGGINGS));
            collect(armorElements.stream().map(map -> map.get(EquipmentSlot.CHEST)), tag(Tags.Items.ARMORS_CHESTPLATES));
            collect(armorElements.stream().map(map -> map.get(EquipmentSlot.HEAD)), tag(Tags.Items.ARMORS_HELMETS));
        }

        private void collect(Stream<? extends RegistryObject<? extends ModArmorItem>> stream, IntrinsicTagAppender<Item> appender) {
            stream.map(RegistryObject::get).forEach(appender::add);
        }
    }

    private static class BlockProvider extends BlockTagsProvider {

        public BlockProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MysticcraftMod.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.CRIMSONIUM_ORE.getBlock(),
                    ModBlocks.GEMSTONE_CRYSTAL.getBlock(), ModBlocks.GEMSTONE_BLOCK.getBlock()
            );

            tag(Tags.Blocks.ORES).add(ModBlocks.CRIMSONIUM_ORE.getBlock(), ModBlocks.GEMSTONE_SEED.getBlock());

            tag(ModBlockTags.FARMABLE).addTag(BlockTags.CROPS).add(Blocks.SUGAR_CANE, Blocks.MELON, Blocks.PUMPKIN, Blocks.CHORUS_FLOWER);

            tag(ModBlockTags.FORAGEABLE).addTag(BlockTags.LOGS);

            tag(ModBlockTags.MINEABLE).addTags(Tags.Blocks.SAND, BlockTags.SNOW, Tags.Blocks.ORES, Tags.Blocks.OBSIDIAN, Tags.Blocks.GRAVEL, Tags.Blocks.NETHERRACK, Tags.Blocks.STONE, Tags.Blocks.END_STONES, Tags.Blocks.COBBLESTONE);
        }
    }
}