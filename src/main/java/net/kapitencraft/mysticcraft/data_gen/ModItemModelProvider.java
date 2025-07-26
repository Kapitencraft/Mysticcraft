package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ModItemModelProvider extends ItemModelProvider {
    //TODO add textures

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("all")
    @Override
    protected void registerModels() {
        makeGemstoneItem();
        makeGemstoneCrystalItems();

        makeElementShards();
        makeRainbowElementShard();

        withExistingParent("blazing_salmon", "item/generated").texture("layer0", "item/blazing_salmon_1").texture("layer1", "item/salmon_head");
        withExistingParent("soul_chain", "item/generated").texture("layer0", "item/soul_chain");
        withExistingParent("cursed_pearl", "item/ender_pearl");

        handHeldItem(ModItems.BUILDERS_WAND, mcLoc("item/netherite_shovel"));
        handHeldItem(ModItems.MOD_DEBUG_STICK, mcLoc("item/stick"));

        handHeldItem(ModItems.STONE_HAMMER, modLoc("item/hammer/stone"));
        handHeldItem(ModItems.IRON_HAMMER, modLoc("item/hammer/iron"));
        handHeldItem(ModItems.DIAMOND_HAMMER, modLoc("item/hammer/diamond"));
        handHeldItem(ModItems.NETHERITE_HAMMER, modLoc("item/hammer/netherite"));

        ModelFile doubleSword = withExistingParent("double_sword", "item/handheld"); //TODO figure out how to change displays

        ModelFile dagger = makeDagger();

        parentItem(ModItems.DIAMOND_DOUBLE_SWORD, modLoc("double_sword"), null);

        simpleItem(ModItems.EMPTY_APPLICABLE_SLOT, mcLoc("block/red_stained_glass"));
        simpleItem(ModItems.MISSING_GEMSTONE_SLOT, mcLoc("block/red_stained_glass"));

        List<RegistryObject<? extends Item>> handhelds = List.of(
                ModItems.AOTE,
                ModItems.AOTV,
                ModItems.ASTREA,
                ModItems.SCYLLA,
                ModItems.HYPERION,
                ModItems.VALKYRIE,
                ModItems.FIRE_LANCE,
                ModItems.BURNING_SCYTHE,
                ModItems.FIERY_SCYTHE,
                ModItems.HEATED_SCYTHE,
                ModItems.GHOSTLY_SWORD,
                ModItems.NECRONS_HANDLE
        );
        handhelds.forEach(itemRegistryObject -> handHeldItem(itemRegistryObject, null));

        List<RegistryObject<? extends Item>> simples = List.of(
                ModItems.BUCKET_OF_MANA,
                ModItems.CRIMSON_STEEL_DUST,
                ModItems.CRIMSON_STEEL_INGOT,
                ModItems.CRIMSONITE_CLUSTER,
                ModItems.CRIMSONITE_DUST,
                ModItems.CRIMSONIUM_DUST,
                ModItems.CRIMSONIUM_INGOT,
                ModItems.RAW_CRIMSONIUM,
                ModItems.RAW_CRIMSONIUM_DUST,
                ModItems.FROZEN_BLAZE_ROD,
                ModItems.HARDENED_TEAR,
                ModItems.HEART_OF_THE_NETHER,
                ModItems.UNBREAKING_CORE,
                ModItems.MS_UPPER_BlADE,
                ModItems.MS_DOWN_BlADE,
                ModItems.WIZARD_HAT,
                ModItems.MAGMA_COD,
                ModItems.ORB_OF_CONSUMPTION,
                ModItems.MANA_STEEL_INGOT,
                ModItems.SHADOW_CRYSTAL,
                ModItems.VOID_TOTEM_ITEM
         );
        simples.forEach(itemRegistryObject -> simpleItem(itemRegistryObject, null));

        List<Map<ArmorItem.Type, ? extends RegistryObject<? extends Item>>> armors = List.of(
                ModItems.CRIMSON_ARMOR,
                ModItems.FROZEN_BLAZE_ARMOR,
                ModItems.SHADOW_ASSASSIN_ARMOR,
                ModItems.ENDER_KNIGHT_ARMOR,
                ModItems.SOUL_MAGE_ARMOR
        );
        armors.stream().flatMap(equipmentSlotMap -> equipmentSlotMap.values().stream())
                        .forEach(registryObject -> simpleItem(registryObject, null));

    }

    private ModelFile makeDagger() {
        return withExistingParent("dagger", "item/generated")
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(0, -90, 50)
                .translation(0, -9, 2)
                .scale(-0.85f, -0.85f, 0.85f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 90, -50)
                .translation(0, -9, 2)
                .scale(-0.85f, -0.85f, 0.85f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 90, -25)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f)
                .end()
                .end();
    }

    private void makeElementShards() {
        for (Element element : Elements.values()) {
            withExistingParent("elemental_shard_of_" + element.getName(), "item/generated")
                    .texture("layer0", modLoc("item/elements/" + element.getName() + "_shard"));
        }
    }

    private void makeRainbowElementShard() {
        withExistingParent("elemental_shard_of_rainbow", "item/generated")
                .texture("layer0", modLoc("item/elements/rainbow_shard"));
    }

    private void makeGemstoneCrystalItems() {
        ItemModelBuilder builder = withExistingParent("gemstone_crystal", "item/generated");
        for (GemstoneCrystal.Size size : GemstoneCrystal.Size.values()) {
            builder
                    .override()
                    .predicate(modLoc("size"), (size.ordinal() + 1) * .1f)
                    .model(makeGemstoneCrystalItemModel(size));
        }
    }

    private ModelFile makeGemstoneCrystalItemModel(GemstoneCrystal.Size size) {
        return withExistingParent("gemstone/crystal/" + size.getSerializedName(), "mysticcraft:item/gemstone_crystal")
                .texture("layer0", modLoc("block/gemstone/crystal/" + size.getSerializedName()));
    }

    private void makeGemstoneItem() {
        ItemModelBuilder builder = withExistingParent("gemstone", "item/generated");
        for (GemstoneType.Rarity rarity : GemstoneType.Rarity.values()) {
            if (rarity != GemstoneType.Rarity.EMPTY)
                builder.override().predicate(modLoc("rarity"), rarity.ordinal() * .1f)
                        .model(makeGemstoneItemModel(rarity));
        }
    }


    private ModelFile makeGemstoneItemModel(GemstoneType.Rarity rarity) {
        return withExistingParent("item/gemstone/item/" + rarity.getId(), "mysticcraft:item/gemstone")
                .texture("layer0", modLoc("item/gemstone/item/" + rarity.getId()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
        return parentItem(item, mcLoc("item/generated"), texture);
    }

    private ItemModelBuilder handHeldItem(RegistryObject<? extends Item> item, @Nullable ResourceLocation texture) {
        return parentItem(item, mcLoc("item/handheld"), texture);
    }

    private ItemModelBuilder parentItem(RegistryObject<? extends Item> item, ResourceLocation parent, @Nullable ResourceLocation texture) {
        return withExistingParent(item.getId().getPath(),
                parent).texture("layer0",
                (texture == null ? modLoc("item/" + item.getId().getPath()) : texture));
    }
}
