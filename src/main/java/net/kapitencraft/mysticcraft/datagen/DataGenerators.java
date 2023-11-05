package net.kapitencraft.mysticcraft.datagen;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        ModLanguageProvider provider = new ModLanguageProvider(output);
        generator.addProvider(false, new ModBlockStateProvider(output, helper));
        generator.addProvider(false, new ModRecipeProvider(output));
        generator.addProvider(true, ModLootTableProvider.create(output));
        generator.addProvider(false, new ModItemModelProvider(output, helper));
        generator.addProvider(false, new ModBestiaryProvider(output, provider));
        generator.addProvider(false, new ReforgeProvider(output));
        generator.addProvider(false, provider);
    }
}