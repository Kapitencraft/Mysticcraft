
package net.kapitencraft.mysticcraft.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class AutoSmeltEnchantment extends Enchantment {
	public AutoSmeltEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, slots);
	}
}
