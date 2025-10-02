package net.kapitencraft.mysticcraft.data_gen;

import com.mojang.blaze3d.platform.NativeImage;
import net.kapitencraft.kap_lib.data_gen.abst.TextureProvider;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModTextureProvider extends TextureProvider {
    public ModTextureProvider(ExistingFileHelper existingFileHelper, PackOutput output) {
        super(existingFileHelper, output);
    }

    @Override
    protected void createEntries() {
        register(new ResourceLocation("item/lapis_lazuli"), MysticcraftMod.res("item/lapis_dust"))
                .then(Transfer.create(new ResourceLocation("item/redstone")));
        register(new ResourceLocation("particle/flame"), MysticcraftMod.res("particle/pale_flame"))
                .then(new Pale());
    }

    protected record Pale() implements Converter {

        @Override
        public NativeImage convert(NativeImage in, ExistingFileHelper helper) {
            return in.mappedCopy(i -> {
                double brightness = TextureProvider.brightness(Color.fromARGBPacked(i));
                return new Color((float) brightness, (float) brightness, (float) brightness, FastColor.ARGB32.alpha(i) / 255f).pack();
            });
        }
    }
}
