package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "spell_and_gemstone"), builder ->
                builder.m_257941_(Component.translatable("itemGroup.spell_and_gemstone"))
                        .m_257737_(() -> new ItemStack(ModItems.STAFF_OF_THE_WILD.get()))
                        .m_257501_((featureFlagSet, output, flag) -> {
                            output.m_246326_(ModItems.STAFF_OF_THE_WILD.get());
                            output.m_246326_(ModItems.HEATED_SCYTHE.get());
                            output.m_246326_(ModItems.FIERY_SCYTHE.get());
                            output.m_246326_(ModItems.BURNING_SCYTHE.get());
                            output.m_246326_(ModItems.INFERNAL_SCYTHE.get());
                            output.m_246326_(ModItems.WIZARD_HAT.get());
                            output.m_246326_(ModItems.SLIVYRA.get());
                            output.m_246326_(ModItems.THE_STAFF_DESTRUCTION.get());
                            addGemstones(ModItems.ALMANDINE_GEMSTONES, output);
                            addGemstones(ModItems.JASPER_GEMSTONES, output);
                            addGemstones(ModItems.RUBY_GEMSTONES, output);
                            addGemstones(ModItems.SAPPHIRE_GEMSTONES, output);
                        }));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "materials"), builder ->
                builder.m_257941_(Component.translatable("itemGroup.materials_mm"))
                        .m_257737_(()-> new ItemStack(ModItems.SPELL_SHARD.get()))
                        .m_257501_((featureFlagSet, output, flag) -> {
                            output.m_246326_(ModItems.SPELL_SHARD.get());
                            output.m_246326_(ModItems.UPPER_BlADE_MS.get());
                            output.m_246326_(ModItems.HEART_OF_THE_NETHER.get());
                            output.m_246326_(ModItems.MANA_STEEL_INGOT.get());
                            output.m_246326_(ModItems.BUCKET_OF_MANA.get());
                            output.m_246326_(ModItems.MS_HANDLE.get());
                            output.m_246326_(ModItems.DOWN_BlADE_MS.get());
                        }));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "weapons_and_tools"), builder ->
                builder.m_257941_(Component.translatable("itemGroup.weapons_and_tools_mm"))
                        .m_257737_(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
                        .m_257501_((featureFlagSet, output, p_260123_) -> {
                            output.m_246326_(ModItems.MANA_STEEL_SWORD.get());
                            addArmor(ModItems.ENDER_KNIGHT_ARMOR, output);
                            addArmor(ModItems.FROZEN_BLAZE_ARMOR, output);
                            addArmor(ModItems.SHADOW_ASSASSIN_ARMOR, output);
                            addArmor(ModItems.SOUL_MAGE_ARMOR, output);
                            output.m_246326_(ModItems.HEATED_SCYTHE.get());
                            output.m_246326_(ModItems.FIERY_SCYTHE.get());
                            output.m_246326_(ModItems.BURNING_SCYTHE.get());
                            output.m_246326_(ModItems.INFERNAL_SCYTHE.get());
                            output.m_246326_(ModItems.SLIVYRA.get());
                            output.m_246326_(ModItems.THE_STAFF_DESTRUCTION.get());
                            output.m_246326_(ModItems.DIAMOND_CLEAVER.get());
                        }));
    }

    private static void addArmor(HashMap<EquipmentSlot, RegistryObject<Item>> armor, CreativeModeTab.Output output) {
        Collection<RegistryObject<Item>> armorPieces = armor.values();
        for (RegistryObject<Item> registryObject : armorPieces) {
            output.m_246326_(registryObject.get());
        }
    }

    private static void addGemstones(HashMap<Gemstone.Rarity, RegistryObject<Item>> gemstone, CreativeModeTab.Output output) {
        Collection<RegistryObject<Item>> gemstones = gemstone.values();
        for (RegistryObject<Item> registryObject : gemstones) {
            output.m_246326_(registryObject.get());
        }
    }
}
