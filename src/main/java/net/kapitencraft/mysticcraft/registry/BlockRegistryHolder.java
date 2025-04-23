package net.kapitencraft.mysticcraft.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistryHolder<T extends Block, K extends BlockItem> {
    private final RegistryObject<T> block;
    private final RegistryObject<K> item;

    public BlockRegistryHolder(RegistryObject<T> block, RegistryObject<K> item) {
        this.block = block;
        this.item = item;
    }

    public K getItem() {
        return item.get();
    }

    public T getBlock() {
        return block.get();
    }

    public Supplier<Block> block() {
        return (Supplier<Block>) block;
    }

    public RegistryObject<K> item() {
        return item;
    }
}
