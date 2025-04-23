package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        blocks.removeAll(NOT_USABLE);
        return blocks;
    }

    private static final List<Block> NOT_USABLE = List.of(ModBlocks.MANA_FLUID_BLOCK.get(), ModBlocks.FRAGILE_BASALT.get(), ModBlocks.DUNGEON_GENERATOR.get());
}
