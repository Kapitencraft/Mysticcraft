package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IArmorEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.item.enchantment.Enchantment;

public class EfficientJewellingEnchantment extends Enchantment implements IToolEnchantment, IWeaponEnchantment, IArmorEnchantment, ModEnchantment {
    public EfficientJewellingEnchantment() {
        super(Rarity.UNCOMMON, MiscUtils.GEMSTONE_ITEM, MiscUtils.allEquipmentSlots);
    }


    public int getMaxLevel() {
        return 100;
    }



    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {"+" + 2*level + "%"};
    }
}
