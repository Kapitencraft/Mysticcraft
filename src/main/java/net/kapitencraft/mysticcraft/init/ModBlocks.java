package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.*;
import net.kapitencraft.mysticcraft.block.deco.*;
import net.kapitencraft.mysticcraft.dungeon.generation.GenerationBlock;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
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
import java.util.List;
import java.util.function.Supplier;

public interface ModBlocks {
    DeferredRegister<Block> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.BLOCKS);
    List<RegistryObject<BlockItem>> ITEM_BLOCKS = new ArrayList<>();
    private static BlockRegistryHolder registerBlock(String name, Supplier<Block> block, Item.Properties properties) {
        RegistryObject<Block> toReturn = REGISTRY.register(name, block);
        return new BlockRegistryHolder(toReturn, registerItem(name, toReturn, properties));
    }

    private static <T extends Block> RegistryObject<BlockItem> registerItem(String name, RegistryObject<T> block, Item.Properties properties) {
        RegistryObject<BlockItem> registryObject = ModItems.REGISTRY.register(name, () -> new BlockItem(block.get(), properties));
        ITEM_BLOCKS.add(registryObject);
        return registryObject;
    }
    BlockRegistryHolder GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, MiscHelper.rarity(Rarity.RARE));

    BlockRegistryHolder REFORGING_ANVIL = registerBlock("reforging_anvil", ReforgeAnvilBlock::new, MiscHelper.rarity(Rarity.UNCOMMON));
    RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = REGISTRY.register("mana_fluid_block", ManaLiquidBlock::new);
    BlockRegistryHolder MANGATIC_STONE = registerBlock("mangatic_stone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)), MiscHelper.rarity(Rarity.RARE));
    BlockRegistryHolder MANGATIC_SLIME = registerBlock("mangatic_slime", MangaticSlimeBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    BlockRegistryHolder OBSIDIAN_PRESSURE_PLATE = registerBlock("obsidian_pressure_plate", ObsidianPressurePlate::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    BlockRegistryHolder CRIMSONIUM_ORE = registerBlock("crimsonium_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), new Item.Properties().rarity(Rarity.UNCOMMON));
    BlockRegistryHolder MANA_SAM_LAUNCHER = registerBlock("mana_sam_launcher", ManaSAMLauncherBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    RegistryObject<Block> FRAGILE_BASALT = REGISTRY.register("fragile_basalt", FragileBasaltBlock::new);
    RegistryObject<Block> GENERATION_BLOCK = REGISTRY.register("generation_block", GenerationBlock::new);
    BlockRegistryHolder GUILD_BOARD = registerBlock("guild_board", GuildBoardBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    BlockRegistryHolder GOLDEN_SLAB = registerBlock("golden_slab", GoldenSlab::new, MiscHelper.rarity(Rarity.COMMON));
    BlockRegistryHolder GOLDEN_STAIRS = registerBlock("golden_stairs", GoldenStairs::new, MiscHelper.rarity(Rarity.COMMON));
    BlockRegistryHolder GOLDEN_WALL = registerBlock("golden_wall", GoldenWall::new, MiscHelper.rarity(Rarity.COMMON));
    BlockRegistryHolder LAPIS_BUTTON = registerBlock("lapis_button", LapisButton::new, MiscHelper.rarity(Rarity.UNCOMMON));
}