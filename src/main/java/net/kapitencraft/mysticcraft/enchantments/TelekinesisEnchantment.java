package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.item.enchantment.Enchantment;

public class TelekinesisEnchantment extends Enchantment {
    public TelekinesisEnchantment() {
        super(Rarity.COMMON, ModEnchantmentCategories.TOOL, MiscUtils.WEAPON_SLOT);
    }
}
