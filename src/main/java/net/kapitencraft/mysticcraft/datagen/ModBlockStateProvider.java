package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.BlockRegistryHolder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MysticcraftMod.MOD_ID, exFileHelper);
    }


    private void blockWithItem(BlockRegistryHolder holder) {
        simpleBlockWithItem(holder.getBlock(), cubeAll(holder.getBlock()));
    }

    @Override
    protected void registerStatesAndModels() {
    }
}
