package net.kapitencraft.mysticcraft.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class BlockRegistryHolder<T extends Block, K extends BlockItem> implements Supplier<T> {
    private final DeferredBlock<T> block;
    private final DeferredItem<K> item;

    public BlockRegistryHolder(DeferredBlock<T> block, DeferredItem<K> item) {
        this.block = block;
        this.item = item;
    }

    public K getItem() {
        return item.get();
    }

    public DeferredItem<K> item() {
        return item;
    }

    @Override
    public T get() {
        return block.get();
    }

    public ResourceLocation getId() {
        return this.block.getId();
    }
}
