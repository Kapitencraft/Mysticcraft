package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
