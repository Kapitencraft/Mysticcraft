package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class UltimateWiseEnchantment extends Enchantment {
    public UltimateWiseEnchantment() {
        super(Rarity.RARE, MISCTools.SPELL_ITEM, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }


    public int getMaxLevel() {
        return 5;
    }

}
