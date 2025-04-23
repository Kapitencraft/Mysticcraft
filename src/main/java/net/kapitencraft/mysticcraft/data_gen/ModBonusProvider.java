package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.BonusProvider;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.AssassinBonus;
import net.kapitencraft.mysticcraft.item.bonus.FreezingAuraBonus;
import net.kapitencraft.mysticcraft.item.bonus.MagicConversionBonus;
import net.kapitencraft.mysticcraft.item.bonus.SoulMageArmorBonus;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ArmorItem;

public class ModBonusProvider extends BonusProvider {
    public ModBonusProvider(PackOutput output) {
        super(output, MysticcraftMod.MOD_ID);
    }

    @Override
    public void register() {
        createSetBonus("freezing_aura").armor(ModItems.FROZEN_BLAZE_ARMOR).setBonus(new FreezingAuraBonus());
        createSetBonus("dominus").armor(ModItems.CRIMSON_ARMOR);
        createSetBonus("mana_syphon").armor(ModItems.SOUL_MAGE_ARMOR).setBonus(new SoulMageArmorBonus());
        createItemBonus(ModItems.SHADOW_DAGGER, "assassin").setBonus(new AssassinBonus());
        createItemBonus(ModItems.SOUL_MAGE_ARMOR.get(ArmorItem.Type.HELMET), "magic_conversion").setBonus(new MagicConversionBonus());
    }
}
