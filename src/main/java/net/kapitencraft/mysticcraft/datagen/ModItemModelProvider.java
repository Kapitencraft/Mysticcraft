package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }


    private List<RegistryObject<Item>> toKeep() {
        List<RegistryObject<Item>> items = new ArrayList<>();
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

    private void registerGemstones() {
        List<Collection<RegistryObject<GemstoneItem>>> gemstoneList = ModItems.GEMSTONES.values().stream().map(HashMap::values).toList();
        List<RegistryObject<GemstoneItem>> gemstones = new ArrayList<>();
        for (Collection<RegistryObject<GemstoneItem>> list : gemstoneList) {
            gemstones.addAll(list);
        }
        for (RegistryObject<GemstoneItem> registryObject : gemstones) {
            GemstoneItem item = registryObject.get();
        }
    }

    private ItemModelBuilder registerWithColor(RegistryObject<Item> item, ResourceLocation texture) {
        return simpleItem(item, texture).texture("layer1", new ResourceLocation(MysticcraftMod.MOD_ID, "item/empty_dyed_overlay"));
    }
}
