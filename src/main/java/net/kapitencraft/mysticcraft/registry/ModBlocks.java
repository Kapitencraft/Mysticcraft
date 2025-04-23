package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.FragileBasaltBlock;
import net.kapitencraft.mysticcraft.block.ManaLiquidBlock;
import net.kapitencraft.mysticcraft.block.MangaticSlimeBlock;
import net.kapitencraft.mysticcraft.block.ReforgeAnvilBlock;
import net.kapitencraft.mysticcraft.block.deco.*;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneGrinderBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.dungeon.generation.DungeonGenerator;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ModBlocks {
    DeferredRegister<Block> REGISTRY = MysticcraftMod.registry(ForgeRegistries.BLOCKS);
    List<RegistryObject<? extends BlockItem>> ITEM_BLOCKS = new ArrayList<>();
    private static <T extends Block> BlockRegistryHolder<T, BlockItem> registerBlock(String name, Supplier<T> block, Item.Properties properties, TabGroup group) {
        return registerBlock(name, block, object -> new BlockItem(object.get(), properties), group);
    }

    private static <T extends Block, K extends BlockItem> BlockRegistryHolder<T, K> registerBlock(String name, Supplier<T> block, Function<RegistryObject<T>, K> func, TabGroup group) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        return new BlockRegistryHolder<>(toReturn, registerItem(name, ()-> func.apply(toReturn), group));
    }

    private static <T, V extends Block> HashMap<T, BlockRegistryHolder<V, BlockItem>> createMappedRegistry(Function<T, V> provider, Function<T, String> nameProvider, Function<T, Item.Properties> propertiesProvider, List<T> values, TabGroup group) {
        HashMap<T, BlockRegistryHolder<V, BlockItem>> map = new HashMap<>();
        for (T t : values) {
            map.put(t, registerBlock(nameProvider.apply(t), ()-> provider.apply(t), propertiesProvider.apply(t), group));
        }
        return map;
    }

    private static <T extends Block, K extends BlockItem> RegistryObject<K> registerItem(String name, Supplier<K> sup, TabGroup tabGroup) {
        RegistryObject<K> registryObject = ModItems.REGISTRY.register(name, sup);
        ITEM_BLOCKS.add(registryObject);
        if (tabGroup != null) {
            tabGroup.add(registryObject);
        }
        return registryObject;
    }
    BlockRegistryHolder<GemstoneGrinderBlock, BlockItem> GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, MiscHelper.rarity(Rarity.RARE), GemstoneItem.GROUP);

    BlockRegistryHolder<ReforgeAnvilBlock, BlockItem> REFORGING_ANVIL = registerBlock("reforge_anvil", ReforgeAnvilBlock::new, MiscHelper.rarity(Rarity.UNCOMMON), TabGroup.MATERIAL);
    RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = REGISTRY.register("mana_fluid_block", ManaLiquidBlock::new);
    BlockRegistryHolder<Block, BlockItem> MANGATIC_STONE = registerBlock("mangatic_stone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)), MiscHelper.rarity(Rarity.RARE), TabGroup.MATERIAL);
    BlockRegistryHolder<MangaticSlimeBlock, BlockItem> MANGATIC_SLIME = registerBlock("mangatic_slime", MangaticSlimeBlock::new, new Item.Properties().rarity(Rarity.EPIC), TabGroup.MATERIAL);
    BlockRegistryHolder<ObsidianPressurePlate, BlockItem> OBSIDIAN_PRESSURE_PLATE = registerBlock("obsidian_pressure_plate", ObsidianPressurePlate::new, new Item.Properties().rarity(Rarity.UNCOMMON), TabGroup.DECO);
    BlockRegistryHolder<Block, BlockItem> CRIMSONIUM_ORE = registerBlock("crimsonium_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), new Item.Properties().rarity(Rarity.UNCOMMON), TabGroup.MATERIAL);
    RegistryObject<Block> FRAGILE_BASALT = REGISTRY.register("fragile_basalt", FragileBasaltBlock::new);
    RegistryObject<Block> DUNGEON_GENERATOR = REGISTRY.register("dungeon_generator", DungeonGenerator::new);
    BlockRegistryHolder<GoldenSlab, BlockItem> GOLDEN_SLAB = registerBlock("golden_slab", GoldenSlab::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<GoldenStairs, BlockItem> GOLDEN_STAIRS = registerBlock("golden_stairs", GoldenStairs::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<GoldenWall, BlockItem> GOLDEN_WALL = registerBlock("golden_wall", GoldenWall::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<LapisButton, BlockItem> LAPIS_BUTTON = registerBlock("lapis_button", LapisButton::new, MiscHelper.rarity(Rarity.UNCOMMON), TabGroup.DECO);
    BlockRegistryHolder<SoulChain, BlockItem> SOUL_CHAIN = registerBlock("soul_chain", SoulChain::new, MiscHelper.rarity(Rarity.RARE), TabGroup.DECO);
    BlockRegistryHolder<GemstoneBlock, GemstoneBlock.Item> GEMSTONE_BLOCK = registerBlock("gemstone_block", GemstoneBlock::new, object -> new GemstoneBlock.Item(), null);
    BlockRegistryHolder<GemstoneCrystal, GemstoneBlock.Item> GEMSTONE_CRYSTAL = registerBlock("gemstone_crystal", GemstoneCrystal::new, object -> new GemstoneCrystal.Item(), null);
    BlockRegistryHolder<GemstoneSeedBlock, GemstoneSeedBlock.Item> GEMSTONE_SEED = registerBlock("gemstone_seed", GemstoneSeedBlock::new, object -> new GemstoneSeedBlock.Item(), null);
}