package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.CRIMSONITE_ORE.getBlock());
        dropSelf(ModBlocks.GEMSTONE_GRINDER.getBlock());
        dropSelf(ModBlocks.MANA_SAM_LAUNCHER.getBlock());
        dropSelf(ModBlocks.MANGATIC_SLIME.getBlock());
        dropSelf(ModBlocks.MANGATIC_STONE.getBlock());
        dropSelf(ModBlocks.OBSIDIAN_PRESSURE_PLATE.getBlock());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
