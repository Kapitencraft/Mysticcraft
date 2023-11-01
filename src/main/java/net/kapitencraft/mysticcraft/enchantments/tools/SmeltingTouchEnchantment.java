package net.kapitencraft.mysticcraft.enchantments.tools;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SmeltingTouchEnchantment extends Enchantment {
    public SmeltingTouchEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.DIGGER, MiscHelper.WEAPON_SLOT);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
