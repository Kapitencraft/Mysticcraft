package net.kapitencraft.mysticcraft.datagen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
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

    @Override
    protected void registerModels() {
        //registerScrolls();
        //registerGemstones();


        List<RegistryObject<Item>> items = List.of();
        for (RegistryObject<Item> item : items) {
            try {
                if (item.get() instanceof TieredItem) {
                    handHeldItem(item, null);
                } else {
                    simpleItem(item, null);
                }
            } catch (IllegalArgumentException e) {
                MysticcraftMod.sendInfo("Unable to find texture '" + item.getId() + "'");
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                 (texture == null ? new ResourceLocation(MysticcraftMod.MOD_ID, "item/" + item.getId().getPath()) : texture));
    }

    private ItemModelBuilder handHeldItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
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
        gemstones = gemstones.stream().filter(registryObject -> registryObject.get().getRarity() != GemstoneType.Rarity.EMPTY).toList();
        for (RegistryObject<GemstoneItem> registryObject : gemstones) {
            GemstoneItem item = registryObject.get();
            registerWithColor(registryObject, getGemstoneTextureForRarity(item.getRarity()));
        }
    }

    private ResourceLocation getGemstoneTextureForRarity(GemstoneType.Rarity rarity) {
        return new ResourceLocation(MysticcraftMod.MOD_ID, "item/" + rarity.getId() + "_gemstone");
    }

    private ItemModelBuilder registerWithColor(RegistryObject<? extends Item> item, ResourceLocation texture) {
        return simpleItem(item, texture).texture("layer1", new ResourceLocation(MysticcraftMod.MOD_ID, "item/empty_dyed_overlay"));
    }
}
