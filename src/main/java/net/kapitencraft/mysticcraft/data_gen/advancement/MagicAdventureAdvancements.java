package net.kapitencraft.mysticcraft.data_gen.advancement;

import net.kapitencraft.kap_lib.mana.advancement.ExtraCriterionTriggers;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MagicAdventureAdvancements implements AdvancementProvider.AdvancementGenerator {
    private static final ResourceLocation BACKGROUND = ResourceLocation.withDefaultNamespace("textures/block/amethyst_block.png");

    @Override
    public void generate(HolderLookup.@NotNull Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        Advancement.Builder.advancement()
                .addCriterion("arch_mage", ExtraCriterionTriggers.MANA_CONSUMED.get().above(10000))
                .display(
                        ModItems.RAINBOW_ELEMENTAL_SHARD,
                        Component.translatable("advancements.mysticcraft.archmage.title"),
                        Component.translatable("advancements.mysticcraft.archmage"),
                        BACKGROUND,
                        AdvancementType.CHALLENGE,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(saver, "mysticcraft:archmage");
    }
}
