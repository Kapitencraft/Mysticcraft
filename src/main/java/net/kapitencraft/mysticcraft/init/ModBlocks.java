package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.GemstoneGrinderBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MysticcraftMod.MOD_ID);
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier block, Item.Properties properties) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerItem(name, toReturn, properties);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerItem(String name, RegistryObject<T> block, Item.Properties properties) {
        return ModItems.REGISTRY.register(name, () -> new BlockItem(block.get(), properties));
    }
    public static final RegistryObject<Block> GEMSTONE_GRINDER = registerBlock("gemstone_grinder", GemstoneGrinderBlock::new, new Item.Properties().rarity(Rarity.RARE));
}
