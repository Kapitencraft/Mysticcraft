package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class SimpleToolEnchantment extends Enchantment {
    protected SimpleToolEnchantment(Rarity p_44676_) {
        super(p_44676_, ModEnchantmentCategories.TOOL, MiscHelper.WEAPON_SLOT);
    }
}
