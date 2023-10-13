package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.*;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class EfficientJewellingEnchantment extends Enchantment implements IToolEnchantment, IWeaponEnchantment, IArmorEnchantment, ModEnchantment {
    public EfficientJewellingEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentCategories.GEMSTONE_ITEM, MiscHelper.allEquipmentSlots);
    }


    public int getMaxLevel() {
        return 100;
    }



    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + 2*level + "%"};
    }
}
