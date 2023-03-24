package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "spell_and_gemstone"), builder ->
                builder.title(Component.translatable("itemGroup.spell_and_gemstone"))
                        .icon(() -> new ItemStack(ModItems.STAFF_OF_THE_WILD.get()))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.accept(ModItems.AOTE.get());
                            output.accept(ModItems.AOTV.get());
                            output.accept(ModItems.STAFF_OF_THE_WILD.get());
                            output.accept(ModItems.HEATED_SCYTHE.get());
                            output.accept(ModItems.FIERY_SCYTHE.get());
                            output.accept(ModItems.BURNING_SCYTHE.get());
                            output.accept(ModItems.INFERNAL_SCYTHE.get());
                            output.accept(ModItems.FIRE_LANCE.get());
                            output.accept(ModItems.WIZARD_HAT.get());
                            output.accept(ModItems.HYPERION.get());
                            output.accept(ModItems.THE_STAFF_DESTRUCTION.get());
                            addGemstones(ModItems.GEMSTONES, output);
                        }));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "materials"), builder ->
                builder.title(Component.translatable("itemGroup.materials_mm"))
                        .icon(()-> new ItemStack(ModItems.SPELL_SHARD.get()))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.accept(ModItems.SPELL_SHARD.get());
                            output.accept(ModItems.UPPER_BlADE_MS.get());
                            output.accept(ModItems.HEART_OF_THE_NETHER.get());
                            output.accept(ModItems.MANA_STEEL_INGOT.get());
                            output.accept(ModItems.BUCKET_OF_MANA.get());
                            output.accept(ModItems.MS_HANDLE.get());
                            output.accept(ModItems.DOWN_BlADE_MS.get());
                            output.accept(ModBlocks.MANGATIC_STONE.getItem());
                            output.accept(ModBlocks.MANGATIC_SLIME.getItem());
                            output.accept(ModBlocks.OBSIDIAN_PRESSURE_PLATE.getItem());
                            output.accept(ModBlocks.CRIMSONITE_ORE.getItem());
                        }));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "weapons_and_tools"), builder ->
                builder.title(Component.translatable("itemGroup.weapons_and_tools_mm"))
                        .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
                        .displayItems((featureFlagSet, output, p_260123_) -> {
                            output.accept(ModItems.MANA_STEEL_SWORD.get());
                            output.accept(ModItems.TALLIN_BOW.get());
                            output.accept(ModItems.LONGBOW.get());
                            addArmor(ModItems.ENDER_KNIGHT_ARMOR, output);
                            addArmor(ModItems.FROZEN_BLAZE_ARMOR, output);
                            addArmor(ModItems.SHADOW_ASSASSIN_ARMOR, output);
                            addArmor(ModItems.SOUL_MAGE_ARMOR, output);
                            addArmor(ModItems.CRIMSON_ARMOR, output);
                            output.accept(ModItems.HEATED_SCYTHE.get());
                            output.accept(ModItems.FIERY_SCYTHE.get());
                            output.accept(ModItems.BURNING_SCYTHE.get());
                            output.accept(ModItems.INFERNAL_SCYTHE.get());
                            output.accept(ModItems.FIRE_LANCE.get());
                            output.accept(ModItems.HYPERION.get());
                            output.accept(ModItems.SCYLLA.get());
                            output.accept(ModItems.ASTREA.get());
                            output.accept(ModItems.VALKYRIE.get());
                            output.accept(ModItems.THE_STAFF_DESTRUCTION.get());
                            output.accept(ModItems.DIAMOND_CLEAVER.get());
                            output.accept(ModItems.IRON_SHIELD.get());
                        }));
    }

    @SubscribeEvent
    public static void addToExisting(CreativeModeTabEvent.BuildContents event) {
        CreativeModeTab tab = event.getTab();
        if (tab == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.FROZEN_BLAZE_SPAWN_EGG);
        }
    }

    private static void addArmor(HashMap<EquipmentSlot, RegistryObject<Item>> armor, CreativeModeTab.Output output) {
        Collection<RegistryObject<Item>> armorPieces = armor.values();
        for (RegistryObject<Item> registryObject : armorPieces) {
            output.accept(registryObject.get());
        }
    }

    private static void addGemstones(HashMap<GemstoneType, HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>>> gemstone, CreativeModeTab.Output output) {
        for (int i = 0; i < GemstoneType.values().length; i++) {
            Collection<RegistryObject<GemstoneItem>> gemstones = new ArrayList<>(gemstone.values()).get(i).values();
                for (RegistryObject<GemstoneItem> registryObject : gemstones) {
                    output.accept(registryObject.get());
                }
        }
    }
}
