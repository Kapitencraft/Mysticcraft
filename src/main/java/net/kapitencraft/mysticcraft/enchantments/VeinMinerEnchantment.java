package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VeinMinerEnchantment extends Enchantment implements IToolEnchantment {
    public VeinMinerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, MiscUtils.WEAPON_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}