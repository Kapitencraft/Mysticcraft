package net.kapitencraft.mysticcraft.data_gen.advancement;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.combat.armor.CrimsonArmorItem;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ModNetherAchievements implements AdvancementProvider.AdvancementGenerator {

    @SuppressWarnings("removal")
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        Advancement.Builder.advancement()
                .parent(ResourceLocation.withDefaultNamespace("nether/netherite_armor"))
                .display(
                        CrimsonArmorItem.createAdvancementStack(),
                        Component.translatable("advancements.mysticcraft.infernal_armor.title"),
                        Component.translatable("advancements.mysticcraft.infernal_armor"),
                        null,
                        AdvancementType.CHALLENGE,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .addCriterion("infernal_looks", createInfernalLooksTrigger(registries.lookupOrThrow(Registries.ITEM)))
                .save(saver, MysticcraftMod.res("nether/infernal_looks"), existingFileHelper);
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> createInfernalLooksTrigger(HolderGetter<Item> items) {
        DataComponentPredicate componentPredicate = DataComponentPredicate.builder()
                .expect(ModDataComponentTypes.TIER.get(), ITieredItem.ItemTier.INFERNAL)
                .build();
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                new ItemPredicate(
                        Optional.of(items.getOrThrow(ItemTags.HEAD_ARMOR)),
                        MinMaxBounds.Ints.ANY,
                        componentPredicate,
                        Map.of()
                ),
                new ItemPredicate(
                        Optional.of(items.getOrThrow(ItemTags.CHEST_ARMOR)),
                        MinMaxBounds.Ints.ANY,
                        componentPredicate,
                        Map.of()
                ),
                new ItemPredicate(
                        Optional.of(items.getOrThrow(ItemTags.LEG_ARMOR)),
                        MinMaxBounds.Ints.ANY,
                        componentPredicate,
                        Map.of()
                ),
                new ItemPredicate(
                        Optional.of(items.getOrThrow(ItemTags.FOOT_ARMOR)),
                        MinMaxBounds.Ints.ANY,
                        componentPredicate,
                        Map.of()
                )
        );
    }
}
