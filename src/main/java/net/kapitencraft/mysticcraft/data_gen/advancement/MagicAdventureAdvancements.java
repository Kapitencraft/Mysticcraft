package net.kapitencraft.mysticcraft.data_gen.advancement;

import net.kapitencraft.mysticcraft.event.advancement.custom.ManaConsumedTrigger;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class MagicAdventureAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement.Builder.advancement()
                .addCriterion("arch_mage", ManaConsumedTrigger.above(10000))
                .display(
                        ModItems.ELEMENTAL_SHARDS.get(Elements.FIRE).get(),
                        Component.translatable("advancements.mysticcraft.archmage.title"),
                        Component.translatable("advancements.mysticcraft.archmage"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(saver, "mysticcraft:archmage");
    }
}
