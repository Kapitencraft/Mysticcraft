package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.*;
import net.kapitencraft.mysticcraft.block.deco.*;
import net.kapitencraft.mysticcraft.dungeon.generation.GenerationBlock;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
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
    DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MysticcraftMod.MOD_ID);
    List<RegistryObject<Item>> ITEM_BLOCKS = new ArrayList<>();
    private static BlockRegistryHolder registerBlock(String name, Supplier<Block> block, Item.Properties properties, ModItems.TabTypes... types) {
        RegistryObject<Block> toReturn = REGISTRY.register(name, block);
        return new BlockRegistryHolder(toReturn, registerItem(name, toReturn, properties, types));
    }

    private static <T extends Block> RegistryObject<Item> registerItem(String name, RegistryObject<T> block, Item.Properties properties, ModItems.TabTypes... types) {
        RegistryObject<Item> registryObject = ModItems.register(name, () -> new BlockItem(block.get(), properties), types);
        ITEM_BLOCKS.add(registryObject);
        return registryObject;
    }
    BlockRegistryHolder GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, MiscUtils.rarity(Rarity.RARE), ModItems.TabTypes.SPELL_AND_GEMSTONE);

    BlockRegistryHolder REFORGING_ANVIL = registerBlock("reforging_anvil", ReforgeAnvilBlock::new, MiscUtils.rarity(Rarity.UNCOMMON));
    RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = REGISTRY.register("mana_fluid_block", ManaLiquidBlock::new);
    BlockRegistryHolder MANGATIC_STONE = registerBlock("mangatic_stone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)), MiscUtils.rarity(Rarity.RARE), ModItems.TabTypes.MOD_MATERIALS);
    BlockRegistryHolder MANGATIC_SLIME = registerBlock("mangatic_slime", MangaticSlimeBlock::new, new Item.Properties().rarity(Rarity.EPIC), ModItems.TabTypes.MOD_MATERIALS);
    BlockRegistryHolder OBSIDIAN_PRESSURE_PLATE = registerBlock("obsidian_pressure_plate", ObsidianPressurePlate::new, new Item.Properties().rarity(Rarity.UNCOMMON), ModItems.TabTypes.MOD_MATERIALS);
    BlockRegistryHolder CRIMSONIUM_ORE = registerBlock("crimsonium_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), new Item.Properties().rarity(Rarity.UNCOMMON), ModItems.TabTypes.MOD_MATERIALS);
    BlockRegistryHolder MANA_SAM_LAUNCHER = registerBlock("mana_sam_launcher", ManaSAMLauncherBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    RegistryObject<Block> FRAGILE_BASALT = REGISTRY.register("fragile_basalt", FragileBasaltBlock::new);
    RegistryObject<Block> GENERATION_BLOCK = REGISTRY.register("generation_block", GenerationBlock::new);
    BlockRegistryHolder GUILD_BOARD = registerBlock("guild_board", GuildBoardBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    BlockRegistryHolder GOLDEN_SLAB = registerBlock("golden_slab", GoldenSlab::new, MiscUtils.rarity(Rarity.COMMON));
    BlockRegistryHolder GOLDEN_STAIRS = registerBlock("golden_stairs", GoldenStairs::new, MiscUtils.rarity(Rarity.COMMON));
    BlockRegistryHolder GOLDEN_WALL = registerBlock("golden_wall", GoldenWall::new, MiscUtils.rarity(Rarity.COMMON));
    BlockRegistryHolder LAPIS_BUTTON = registerBlock("lapis_button", LapisButton::new, MiscUtils.rarity(Rarity.UNCOMMON));
}