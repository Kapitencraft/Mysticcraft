package net.kapitencraft.mysticcraft.enchantments.tools;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class FarmingToolEnchantment extends Enchantment {
    protected FarmingToolEnchantment(Rarity p_44676_) {
        super(p_44676_, ModEnchantmentCategories.FARMING_TOOLS, MiscHelper.WEAPON_SLOT);
    }
}
