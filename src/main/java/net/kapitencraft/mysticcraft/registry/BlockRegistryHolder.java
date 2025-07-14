package net.kapitencraft.mysticcraft.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistryHolder<T extends Block, K extends BlockItem> implements Supplier<T> {
    private final RegistryObject<T> block;
    private final RegistryObject<K> item;

    public BlockRegistryHolder(RegistryObject<T> block, RegistryObject<K> item) {
        this.block = block;
        this.item = item;
    }

    public K getItem() {
        return item.get();
    }

    public RegistryObject<K> item() {
        return item;
    }

    @Override
    public T get() {
        return block.get();
    }
}
