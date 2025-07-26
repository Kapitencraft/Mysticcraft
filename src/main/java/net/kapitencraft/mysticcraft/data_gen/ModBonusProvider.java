package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.BonusProvider;
import net.kapitencraft.kap_lib.item.bonus.type.AttributeModifiersBonus;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.*;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ModBonusProvider extends BonusProvider {

    public ModBonusProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, MysticcraftMod.MOD_ID, pLookupProvider, existingFileHelper);
    }

    @Override
    public void register() {
        createSetBonus("freezing_aura").armor(ModItems.FROZEN_BLAZE_ARMOR).setBonus(new FreezingAuraBonus());
        createSetBonus("dominus").armor(ModItems.CRIMSON_ARMOR).setBonus(new DominusBonus());
        createSetBonus("hydra").armor(ModItems.TERROR_ARMOR).setBonus(new HydraBonus());
        createSetBonus("mana_syphon").armor(ModItems.SOUL_MAGE_ARMOR).setBonus(new ManaSyphonBonus());
        createItemBonus(ModItems.SHADOW_DAGGER, "assassin").setBonus(new AssassinBonus());
        createItemBonus(ModItems.SOUL_MAGE_ARMOR.get(ArmorItem.Type.HELMET), "magic_conversion").setBonus(new MagicConversionBonus());
        createItemBonus(ModItems.SOUL_MAGE_ARMOR.get(ArmorItem.Type.CHESTPLATE), "mana_reservoir").setBonus(
                AttributeModifiersBonus.builder()
                        .addModifier(ExtraAttributes.MAX_MANA, new AttributeModifier(UUID.fromString("ff5f01b1-85c2-482b-be0e-02eff7211613"),  "ManaReservoir", .2, AttributeModifier.Operation.MULTIPLY_TOTAL))
                        .build()
        );
    }
}
