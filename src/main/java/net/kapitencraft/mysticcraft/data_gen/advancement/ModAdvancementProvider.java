package net.kapitencraft.mysticcraft.data_gen.advancement;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(
                new MagicAdventureAdvancements(),
                new ModNetherAchievements()
        ));
    }

    //public static CriterionTriggerInstance forTag(HolderLookup.Provider registries, TagKey<Item> key) {
    //    return InventoryChangeTrigger.TriggerInstance.hasItems(
    //            registries.lookupOrThrow(Registries.ITEM)
    //                    .getOrThrow(key)
    //                    .contents()
    //                    .stream()
    //                    .map(Holder::get)
    //                    .toArray(Item[]::new)
    //    );
    //}
}
