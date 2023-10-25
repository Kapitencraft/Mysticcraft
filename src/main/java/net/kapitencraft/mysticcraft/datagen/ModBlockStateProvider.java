package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.BlockRegistryHolder;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MysticcraftMod.MOD_ID, exFileHelper);
    }


    private void blockWithItem(BlockRegistryHolder<?> holder) {
        simpleBlockWithItem(holder.getBlock(), cubeAll(holder.getBlock()));
    }

    @Override
    protected void registerStatesAndModels() {
        registerGemstones();
    }

    private void registerGemstones() {
        ModBlocks.GEMSTONE_BLOCKS.forEach((type, blockRegistryHolder) -> {
           simpleBlockWithItem(blockRegistryHolder.getBlock(), models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryHolder.getBlock()).getPath(), new ResourceLocation(MysticcraftMod.MOD_ID, "block/gemstone_crystal")));
        });
    }
}
