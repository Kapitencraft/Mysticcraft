package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerScrolls();
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item, @Nullable String texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MysticcraftMod.MOD_ID, (texture == null ? "item/" + item.getId().getPath() : texture)));
    }

    private void registerScrolls() {
        for (RegistryObject<Item> item : ModItems.SCROLLS.values()) {
            simpleItem(item, "item/scroll");
        }
    }
}
