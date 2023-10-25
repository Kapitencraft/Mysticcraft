package net.kapitencraft.mysticcraft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistryHolder<T extends Block> {
    private final RegistryObject<T> block;
    private final RegistryObject<BlockItem> item;

    public BlockRegistryHolder(RegistryObject<T> block, RegistryObject<BlockItem> item) {
        this.block = block;
        this.item = item;
    }

    public BlockItem getItem() {
        return item.get();
    }

    public T getBlock() {
        return block.get();
    }

    public Supplier<Block> block() {
        return (Supplier<Block>) block;
    }
}
