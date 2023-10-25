package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.*;
import net.kapitencraft.mysticcraft.block.deco.*;
import net.kapitencraft.mysticcraft.dungeon.generation.GenerationBlock;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
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
import java.util.function.Supplier;

public interface ModBlocks {
    DeferredRegister<Block> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.BLOCKS);
    List<RegistryObject<BlockItem>> ITEM_BLOCKS = new ArrayList<>();
    private static <T extends Block> BlockRegistryHolder<T> registerBlock(String name, Supplier<T> block, Item.Properties properties, TabGroup group) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        return new BlockRegistryHolder<>(toReturn, registerItem(name, toReturn, properties, group));
    }

    private static <T, V extends Block> HashMap<T, BlockRegistryHolder<V>> createMappedRegistry(Provider<V, T> provider, Provider<String, T> nameProvider, Provider<Item.Properties, T> propertiesProvider, List<T> values, TabGroup group) {
        HashMap<T, BlockRegistryHolder<V>> map = new HashMap<>();
        for (T t : values) {
            map.put(t, registerBlock(nameProvider.provide(t), ()-> provider.provide(t), propertiesProvider.provide(t), group));
        }
        return map;
    }

    private static <T extends Block> RegistryObject<BlockItem> registerItem(String name, RegistryObject<T> block, Item.Properties properties, TabGroup tabGroup) {
        RegistryObject<BlockItem> registryObject = ModItems.REGISTRY.register(name, () -> new BlockItem(block.get(), properties));
        ITEM_BLOCKS.add(registryObject);
        if (tabGroup != null) {
            tabGroup.add(registryObject);
        }
        return registryObject;
    }
    BlockRegistryHolder<GemstoneGrinderBlock> GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, MiscHelper.rarity(Rarity.RARE), GemstoneItem.GROUP);

    BlockRegistryHolder<ReforgeAnvilBlock> REFORGING_ANVIL = registerBlock("reforging_anvil", ReforgeAnvilBlock::new, MiscHelper.rarity(Rarity.UNCOMMON), TabGroup.MATERIAL);
    RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = REGISTRY.register("mana_fluid_block", ManaLiquidBlock::new);
    BlockRegistryHolder<Block> MANGATIC_STONE = registerBlock("mangatic_stone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)), MiscHelper.rarity(Rarity.RARE), TabGroup.MATERIAL);
    BlockRegistryHolder<MangaticSlimeBlock> MANGATIC_SLIME = registerBlock("mangatic_slime", MangaticSlimeBlock::new, new Item.Properties().rarity(Rarity.EPIC), TabGroup.MATERIAL);
    BlockRegistryHolder<ObsidianPressurePlate> OBSIDIAN_PRESSURE_PLATE = registerBlock("obsidian_pressure_plate", ObsidianPressurePlate::new, new Item.Properties().rarity(Rarity.UNCOMMON), TabGroup.DECO);
    BlockRegistryHolder<Block> CRIMSONIUM_ORE = registerBlock("crimsonium_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), new Item.Properties().rarity(Rarity.UNCOMMON), TabGroup.MATERIAL);
    BlockRegistryHolder<ManaSAMLauncherBlock> MANA_SAM_LAUNCHER = registerBlock("mana_sam_launcher", ManaSAMLauncherBlock::new, new Item.Properties().rarity(Rarity.EPIC), null);
    RegistryObject<Block> FRAGILE_BASALT = REGISTRY.register("fragile_basalt", FragileBasaltBlock::new);
    RegistryObject<Block> GENERATION_BLOCK = REGISTRY.register("generation_block", GenerationBlock::new);
    BlockRegistryHolder<GuildBoardBlock> GUILD_BOARD = registerBlock("guild_board", GuildBoardBlock::new, new Item.Properties().rarity(Rarity.EPIC), TabGroup.MATERIAL);
    BlockRegistryHolder<GoldenSlab> GOLDEN_SLAB = registerBlock("golden_slab", GoldenSlab::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<GoldenStairs> GOLDEN_STAIRS = registerBlock("golden_stairs", GoldenStairs::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<GoldenWall> GOLDEN_WALL = registerBlock("golden_wall", GoldenWall::new, MiscHelper.rarity(Rarity.COMMON), TabGroup.GOLDEN_DECO);
    BlockRegistryHolder<LapisButton> LAPIS_BUTTON = registerBlock("lapis_button", LapisButton::new, MiscHelper.rarity(Rarity.UNCOMMON), TabGroup.DECO);
    HashMap<GemstoneType, BlockRegistryHolder<GemstoneBlock>> GEMSTONE_BLOCKS = createMappedRegistry(GemstoneBlock::new, value -> value.getId() + "_gemstone_block", value -> MiscHelper.rarity(Rarity.RARE), List.of(GemstoneType.values()), TabGroup.MATERIAL);
}