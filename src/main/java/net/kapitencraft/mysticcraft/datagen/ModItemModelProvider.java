package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }


    private List<RegistryObject<Item>> toKeep() {
        List<RegistryObject<Item>> items = new ArrayList<>();
        items.add(ModItems.RAW_CRIMSONIUM);
        items.add(ModItems.RAW_CRIMSONIUM_DUST);
        return items;
    }


    @Override
    protected void registerModels() {
        //registerScrolls();
        List<RegistryObject<Item>> items = toKeep();
        for (RegistryObject<Item> item : items) {
            if (item.get() instanceof TieredItem) {
                handHeldItem(item, null);
            } else {
                simpleItem(item, null);
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                 (texture == null ? new ResourceLocation(MysticcraftMod.MOD_ID, "item/" + item.getId().getPath()) : texture));
    }

    private ItemModelBuilder handHeldItem(RegistryObject<Item> item, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                (texture == null ? new ResourceLocation(MysticcraftMod.MOD_ID, "item/" + item.getId().getPath()) : texture));
    }

    private void registerScrolls() {
        for (RegistryObject<Item> item : ModItems.SCROLLS.values()) {
            simpleItem(item, new ResourceLocation(MysticcraftMod.MOD_ID, "item/scroll"));
        }
    }
}
