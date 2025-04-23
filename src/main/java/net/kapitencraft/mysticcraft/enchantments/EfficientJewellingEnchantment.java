package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.kap_lib.enchantments.abstracts.IArmorEnchantment;
import net.kapitencraft.kap_lib.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.kap_lib.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.kap_lib.enchantments.abstracts.ModEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EfficientJewellingEnchantment extends Enchantment implements IToolEnchantment, IWeaponEnchantment, IArmorEnchantment, ModEnchantment {
    public EfficientJewellingEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentCategories.GEMSTONE_ITEM, EquipmentSlot.values());
    }


    public int getMaxLevel() {
        return 25;
    }



    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + 8*level + "%"};
    }
}
