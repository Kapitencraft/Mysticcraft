package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.*;
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

public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MysticcraftMod.MOD_ID);
    public static List<RegistryObject<Item>> ITEM_BLOCKS = new ArrayList<>();
    private static BlockRegistryHolder registerBlock(String name, Supplier<Block> block, Item.Properties properties) {
        RegistryObject<Block> toReturn = REGISTRY.register(name, block);
        return new BlockRegistryHolder(toReturn, registerItem(name, toReturn, properties));
    }

    private static <T extends Block> RegistryObject<Item> registerItem(String name, RegistryObject<T> block, Item.Properties properties) {
        RegistryObject<Item> registryObject = ModItems.REGISTRY.register(name, () -> new BlockItem(block.get(), properties));
        ITEM_BLOCKS.add(registryObject);
        return registryObject;
    }
    public static final BlockRegistryHolder GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, new Item.Properties().rarity(Rarity.RARE));
    public static final RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = REGISTRY.register("mana_fluid_block", ManaLiquidBlock::new);
    public static final BlockRegistryHolder MANGATIC_STONE = registerBlock("mangatic_stone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)), new Item.Properties().rarity(Rarity.RARE));
    public static final BlockRegistryHolder MANGATIC_SLIME = registerBlock("mangatic_slime", MangaticSlimeBlock::new, new Item.Properties().rarity(Rarity.EPIC));
    public static final BlockRegistryHolder OBSIDIAN_PRESSURE_PLATE = registerBlock("obsidian_pressure_plate", ObsidianPressurePlate::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final BlockRegistryHolder CRIMSONIUM_ORE = registerBlock("crimsonium_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final BlockRegistryHolder MANA_SAM_LAUNCHER = registerBlock("mana_sam_launcher", ManaSAMLauncherBlock::new, new Item.Properties().rarity(Rarity.EPIC));
}