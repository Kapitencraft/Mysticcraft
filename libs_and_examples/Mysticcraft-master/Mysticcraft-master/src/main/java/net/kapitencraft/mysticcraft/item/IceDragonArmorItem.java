
package net.kapitencraft.mysticcraft.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import net.kapitencraft.mysticcraft.init.MysticcraftModItems;

import java.util.List;

public abstract class IceDragonArmorItem extends ArmorItem {
	public IceDragonArmorItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
			}

			@Override
			public int getDefenseForSlot(EquipmentSlot slot) {
				return new int[]{5, 6, 8, 5}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.growl"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(MysticcraftModItems.ICE_DRAGON_SCALE.get()));
			}

			@Override
			public String getName() {
				return "ice_dragon_armor";
			}

			@Override
			public float getToughness() {
				return 2.5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.11f;
			}
		}, slot, properties);
	}

	public static class Helmet extends IceDragonArmorItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TextComponent("\u00A7b+50 Intelligence"));
			list.add(new TextComponent("\u00A74+3 Strength"));
			list.add(new TextComponent("\u00A76Full Set Ability:  Ice Aura"));
			list.add(new TextComponent("Every mob 4 blocks around you is slowed and takes"));
			list.add(new TextComponent("40 damage per second"));
			list.add(new TextComponent("\u00A74Mana Cost: 100/sec"));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "mysticcraft:textures/models/armor/ice_dragon__layer_1.png";
		}
	}

	public static class Chestplate extends IceDragonArmorItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TextComponent("\u00A7b+50 Intelligence"));
			list.add(new TextComponent("\u00A74+3 Strength"));
			list.add(new TextComponent("\u00A76Full Set Ability:  Ice Aura"));
			list.add(new TextComponent("Every mob 4 blocks around you is slowed and takes"));
			list.add(new TextComponent("40 damage per second"));
			list.add(new TextComponent("\u00A74Mana Cost: 100/sec"));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "mysticcraft:textures/models/armor/ice_dragon__layer_1.png";
		}
	}

	public static class Leggings extends IceDragonArmorItem {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TextComponent("\u00A7b+50 Intelligence"));
			list.add(new TextComponent("\u00A74+3 Strength"));
			list.add(new TextComponent("\u00A76Full Set Ability:  Ice Aura"));
			list.add(new TextComponent("Every mob 4 blocks around you is slowed and takes"));
			list.add(new TextComponent("40 damage per second"));
			list.add(new TextComponent("\u00A74Mana Cost: 100/sec"));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "mysticcraft:textures/models/armor/ice_dragon__layer_2.png";
		}
	}

	public static class Boots extends IceDragonArmorItem {
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new TextComponent("\u00A7b+50 Intelligence"));
			list.add(new TextComponent("\u00A74+3 Strength"));
			list.add(new TextComponent("\u00A76Full Set Ability:  Ice Aura"));
			list.add(new TextComponent("Every mob 4 blocks around you is slowed and takes"));
			list.add(new TextComponent("40 damage per second"));
			list.add(new TextComponent("\u00A74Mana Cost: 100/sec"));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "mysticcraft:textures/models/armor/ice_dragon__layer_1.png";
		}
	}
}
