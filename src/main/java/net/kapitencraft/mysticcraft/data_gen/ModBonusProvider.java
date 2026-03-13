package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.bonus.datagen.BonusProvider;
import net.kapitencraft.kap_lib.bonus.type.AttributeModifiersBonus;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.*;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBonusProvider extends BonusProvider {

    public ModBonusProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, pLookupProvider, existingFileHelper);
    }

    @Override
    public void register() {
        createSetBonus("freezing_aura").armor(ModItems.FROZEN_BLAZE_ARMOR).setBonus(FreezingAuraBonus.INSTANCE);
        createSetBonus("dominus").armor(ModItems.CRIMSON_ARMOR).setBonus(DominusBonus.INSTANCE);
        createSetBonus("hydra").armor(ModItems.TERROR_ARMOR).setBonus(HydraBonus.INSTANCE);
        createSetBonus("mana_syphon").armor(ModItems.SOUL_MAGE_ARMOR).setBonus(ManaSyphonBonus.INSTANCE);
        createItemBonus(ModItems.SHADOW_DAGGER, "assassin").setBonus(AssassinBonus.INSTANCE);
        createItemBonus(ModItems.SOUL_MAGE_ARMOR.get(ArmorItem.Type.CHESTPLATE), "mana_reservoir").setBonus(
                AttributeModifiersBonus.builder()
                        .addModifier(ManaAttributes.MAX_MANA, new AttributeModifier(MysticcraftMod.res("mana_reservoir"), .2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                        .build()
        );
    }
}
