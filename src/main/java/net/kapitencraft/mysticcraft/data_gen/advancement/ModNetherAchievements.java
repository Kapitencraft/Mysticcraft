package net.kapitencraft.mysticcraft.data_gen.advancement;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.combat.armor.CrimsonArmorItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModNetherAchievements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement.Builder.advancement()
                .parent(new ResourceLocation("nether/netherite_armor"))
                .display(
                        CrimsonArmorItem.createAdvancementStack(),
                        Component.translatable("advancements.mysticcraft.infernal_armor.title"),
                        Component.translatable("advancements.mysticcraft.infernal_armor"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .addCriterion("infernal_looks", createInfernalLooksTrigger())
                .save(saver, MysticcraftMod.res("nether/infernal_looks"), existingFileHelper);
    }

    private static CriterionTriggerInstance createInfernalLooksTrigger() {
        NbtPredicate predicate = new NbtPredicate(ITieredItem.ItemTier.INFERNAL.createTag());
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                new ItemPredicate(
                        Tags.Items.ARMORS_HELMETS,
                        null,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE,
                        EnchantmentPredicate.NONE,
                        null,
                        predicate
                        ),
                new ItemPredicate(
                        Tags.Items.ARMORS_CHESTPLATES,
                        null,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE,
                        EnchantmentPredicate.NONE,
                        null,
                        predicate
                ),
                new ItemPredicate(
                        Tags.Items.ARMORS_LEGGINGS,
                        null,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE,
                        EnchantmentPredicate.NONE,
                        null,
                        predicate
                ),
                new ItemPredicate(
                        Tags.Items.ARMORS_BOOTS,
                        null,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE,
                        EnchantmentPredicate.NONE,
                        null,
                        predicate
                )
        );
    }
}
