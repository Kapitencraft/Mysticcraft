package net.kapitencraft.mysticcraft.data_gen;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.data_gen.advancement.ModAdvancementProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ModLanguageProvider provider = new ModLanguageProvider(output);
        generator.addProvider(true, new ModTextureProvider(helper, output));
        generator.addProvider(true, new ModBlockStateProvider(output, helper));
        generator.addProvider(true, new ModRecipeProvider(output));
        generator.addProvider(false, ModLootTableProvider.create(output));
        generator.addProvider(true, new ModItemModelProvider(output, helper));
        generator.addProvider(false, new BestiaryProvider(output, provider));
        generator.addProvider(true, new ReforgeProvider(output, "mysticcraft"));
        //generator.addProvider(event.includeClient(), provider);
        registries = generator.addProvider(true, new ModRegistryProvider(output, registries)).getRegistryProvider();
        ModTagProvider.Block blockTags = generator.addProvider(true, new ModTagProvider.Block(output, registries, helper));
        generator.addProvider(true, new ModTagProvider.Item(output, registries, blockTags.contentsGetter(), helper));
        generator.addProvider(true, new ModTagProvider.Biome(output, registries, helper));
        generator.addProvider(true, new ModTagProvider.Entity(output, registries, helper));
        generator.addProvider(true, new ModTagProvider.DamageTypes(output, registries, helper));
        generator.addProvider(true, new ModAdvancementProvider(output, registries, helper));
        generator.addProvider(true, new ModItemRequirementsProvider(output));
        generator.addProvider(true, new ModBonusProvider(output, registries, helper));
        generator.addProvider(true, new PerkProvider(output, registries));
    }
}