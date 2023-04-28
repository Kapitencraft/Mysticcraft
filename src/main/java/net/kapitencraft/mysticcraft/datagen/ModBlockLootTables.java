package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.CRIMSONIUM_ORE.getBlock());
        dropSelf(ModBlocks.GEMSTONE_GRINDER.getBlock());
        dropSelf(ModBlocks.MANA_SAM_LAUNCHER.getBlock());
        dropSelf(ModBlocks.MANGATIC_SLIME.getBlock());
        dropSelf(ModBlocks.MANGATIC_STONE.getBlock());
        dropSelf(ModBlocks.OBSIDIAN_PRESSURE_PLATE.getBlock());
        add(Blocks.NETHERRACK, (block -> createOreDrop(block, ModItems.CRIMSONITE_CLUSTER.get())));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
