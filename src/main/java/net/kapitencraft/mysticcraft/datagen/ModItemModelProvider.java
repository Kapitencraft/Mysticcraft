package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {
    //TODO add textures

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("all")
    @Override
    protected void registerModels() {


        List<RegistryObject<Item>> items = List.of(

        );
        for (RegistryObject<Item> item : items) {
            try {
                if (item.get() instanceof TieredItem) {
                    handHeldItem(item, null);
                } else {
                    simpleItem(item, null);
                }
            } catch (IllegalArgumentException e) {
                MysticcraftMod.LOGGER.info("Unable to find texture '{}'", item.getId());
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                 (texture == null ? MysticcraftMod.res("item/" + item.getId().getPath()) : texture));
    }

    private ItemModelBuilder handHeldItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                (texture == null ? MysticcraftMod.res("item/" + item.getId().getPath()) : texture));
    }
}
