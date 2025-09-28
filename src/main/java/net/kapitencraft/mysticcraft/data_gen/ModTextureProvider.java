package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.TextureProvider;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModTextureProvider extends TextureProvider {
    public ModTextureProvider(ExistingFileHelper existingFileHelper, PackOutput output) {
        super(existingFileHelper, output);
    }

    @Override
    protected void createEntries() {
        register(new ResourceLocation("item/lapis_lazuli"), MysticcraftMod.res("item/lapis_dust"))
                .then(Transfer.create(new ResourceLocation("item/redstone")));
    }
}
