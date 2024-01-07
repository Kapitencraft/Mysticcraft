package net.kapitencraft.mysticcraft.enchantments.tools;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FlashEnchantment extends Enchantment implements ModEnchantment {


    public FlashEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.FISHING_ROD, MiscHelper.WEAPON_SLOT);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{level * 5 + "", level * 15 + ""};
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
