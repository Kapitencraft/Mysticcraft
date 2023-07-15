package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

public class ArmorAbilityUseProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double JumpMultiply = 0;
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(),
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) == 0) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)
					.getItem() == MysticcraftModItems.SPIDER_ARMOR_BOOTS.get()
					&& (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 50) {
				JumpMultiply = 1;
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_LEGGINGS.get()) {
					JumpMultiply = JumpMultiply + 1;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_CHESTPLATE.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_HELMET.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				{
					double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 50;
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Mana = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), JumpMultiply, (entity.getDeltaMovement().z())));
			} else if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)
					.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_BOOTS.get()) {
				JumpMultiply = 2;
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_LEGGINGS.get()) {
					JumpMultiply = JumpMultiply + 1.5;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_CHESTPLATE.get()) {
					JumpMultiply = JumpMultiply + 1;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_HELMET.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				{
					double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 50;
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Mana = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), JumpMultiply, (entity.getDeltaMovement().z())));
			}
		} else {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)
					.getItem() == MysticcraftModItems.SPIDER_ARMOR_BOOTS.get()
					&& (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= (50 / 10)
									* EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(),
											(entity instanceof LivingEntity _entGetArmor
													? _entGetArmor.getItemBySlot(EquipmentSlot.FEET)
													: ItemStack.EMPTY))) {
				JumpMultiply = 1;
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_LEGGINGS.get()) {
					JumpMultiply = JumpMultiply + 1;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_CHESTPLATE.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.SPIDER_ARMOR_HELMET.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				{
					double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
							- (50 / 10) * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(),
									(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY));
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Mana = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), JumpMultiply, (entity.getDeltaMovement().z())));
			} else if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)
					.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_BOOTS.get()
					&& (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= (50 / 10)
									* EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(),
											(entity instanceof LivingEntity _entGetArmor
													? _entGetArmor.getItemBySlot(EquipmentSlot.FEET)
													: ItemStack.EMPTY))) {
				JumpMultiply = 2;
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_LEGGINGS.get()) {
					JumpMultiply = JumpMultiply + 1.5;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_CHESTPLATE.get()) {
					JumpMultiply = JumpMultiply + 1;
				}
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)
						.getItem() == MysticcraftModItems.TARRANTULA_ARMOR_HELMET.get()) {
					JumpMultiply = JumpMultiply + 0.5;
				}
				{
					double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
							- (50 / 10) * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(),
									(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY));
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Mana = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), JumpMultiply, (entity.getDeltaMovement().z())));
			}
		}
	}
}
