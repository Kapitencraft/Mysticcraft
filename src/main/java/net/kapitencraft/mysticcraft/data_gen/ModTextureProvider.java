package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.datagen.TextureProvider;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModTextureProvider extends TextureProvider {
    public ModTextureProvider(ExistingFileHelper existingFileHelper, PackOutput output) {
        super(existingFileHelper, output);
    }

    @Override
    protected void createEntries() {
        register(ResourceLocation.withDefaultNamespace("item/lapis_lazuli"), MysticcraftMod.res("item/lapis_dust"))
                .then(Transfer.create(ResourceLocation.withDefaultNamespace("item/redstone")));
        register(ResourceLocation.withDefaultNamespace("particle/flame"), MysticcraftMod.res("particle/pale_flame"))
                .then(Pale.INSTANCE);
        register(MysticcraftMod.res("item/elements/rainbow_shard"), MysticcraftMod.res("item/rainbow_sword"))
                .then(Transfer.createWithMask(ResourceLocation.withDefaultNamespace("item/diamond_sword"), TextureProvider.SWORD_MASK));
    }
}
