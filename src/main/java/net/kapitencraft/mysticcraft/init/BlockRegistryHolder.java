package net.kapitencraft.mysticcraft.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistryHolder {
    private final RegistryObject<Block> block;
    private final RegistryObject<Item> item;

    public BlockRegistryHolder(RegistryObject<Block> block, RegistryObject<Item> item) {
        this.block = block;
        this.item = item;
    }

    public Item getItem() {
        return item.get();
    }

    public Block getBlock() {
        return block.get();
    }
}
