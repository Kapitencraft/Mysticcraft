package net.kapitencraft.mysticcraft.data_gen;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.data_gen.advancement.ModAdvancementProvider;
import net.kapitencraft.mysticcraft.data_gen.registry.ModRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID)
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
        generator.addProvider(true, new ModItemModelProvider(output, helper));
        generator.addProvider(true, new ReforgeProvider(output, "mysticcraft"));
        //generator.addProvider(event.includeClient(), provider);
        registries = generator.addProvider(true, new ModRegistryProvider(output, registries)).getRegistryProvider();
        ModTagProvider.Block blockTags = generator.addProvider(true, new ModTagProvider.Block(output, registries, helper));
        generator.addProvider(true, ModLootTableProvider.create(output, registries));
        generator.addProvider(true, new ModRecipeProvider(output, registries));
        generator.addProvider(true, new ModTagProvider.Items(output, registries, blockTags.contentsGetter(), helper));
        generator.addProvider(true, new ModTagProvider.Biome(output, registries, helper));
        generator.addProvider(true, new ModTagProvider.Entity(output, registries, helper));
        generator.addProvider(true, new ModTagProvider.DamageTypes(output, registries, helper));
        generator.addProvider(true, new ModAdvancementProvider(output, registries, helper));
        generator.addProvider(true, new ModItemRequirementsProvider(output));
        generator.addProvider(true, new ModBonusProvider(output, registries, helper));
        generator.addProvider(true, new ModDataMapsProvider(output, registries));
    }
}