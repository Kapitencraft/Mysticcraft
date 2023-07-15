package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class YeetThornsPDProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack armor_feet = ItemStack.EMPTY;
		ItemStack armor_leggings = ItemStack.EMPTY;
		ItemStack armor_chest = ItemStack.EMPTY;
		ItemStack armor_helmet = ItemStack.EMPTY;
		armor_feet = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
		armor_leggings = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
		armor_chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY);
		armor_helmet = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY);
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_feet) > 0) {
			sourceentity.setDeltaMovement(
					new Vec3(0, (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_feet) / 1.5), 0));
		} else if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_leggings) > 0) {
			sourceentity.setDeltaMovement(
					new Vec3(0, (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_leggings) / 1.5), 0));
		} else if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_chest) > 0) {
			sourceentity.setDeltaMovement(
					new Vec3(0, (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_chest) / 1.5), 0));
		} else if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_helmet) > 0) {
			sourceentity.setDeltaMovement(
					new Vec3(0, (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET_THORNS.get(), armor_helmet) / 1.5), 0));
		}
	}
}
