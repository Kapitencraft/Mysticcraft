
package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import net.kapitencraft.mysticcraft.init.MysticcraftModItems;

import java.util.List;

public class SlivyraItem extends SwordItem {
	public SlivyraItem() {
		super(new Tier() {
			public int getUses() {
				return 10000;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 10f;
			}

			public int getLevel() {
				return 1;
			}

			public int getEnchantmentValue() {
				return 21;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(MysticcraftModItems.LIGHTNING_STAFF.get()));
			}
		}, 3, -1.2f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("You can add Spells to this item."));
		list.add(new TextComponent("By right-clicking on a Block"));
		list.add(new TextComponent("\u00A71\u00A7l\u00A7kr\u00A7r\u00A71\u00A7lMythic Weapon\u00A71\u00A7kr"));
	}
}
