
package net.kapitencraft.mysticcraft.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;

import net.kapitencraft.mysticcraft.init.MysticcraftModItems;

import java.util.List;

public class UltimateWiseEnchantment extends Enchantment {
	public UltimateWiseEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, slots);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		Item item = stack.getItem();
		return List.of(MysticcraftModItems.AMETHIST_SWORD.get(), MysticcraftModItems.WANDOF_HEALING.get(), MysticcraftModItems.WANDOF_MENDING.get(),
				MysticcraftModItems.WANDOF_REGENERATION.get(), MysticcraftModItems.AOTV.get(), MysticcraftModItems.AOTE.get(),
				MysticcraftModItems.LIGHTNING_RUSH.get(), MysticcraftModItems.LIGHTNING_SHIELD_SPELL.get(),
				MysticcraftModItems.BEAM_ME_UP_SPELL.get(), MysticcraftModItems.SHADOW_WARP.get()).contains(item);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}
}
