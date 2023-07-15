package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class StrengthChangeProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double IntAdd = 0;
		ItemStack helmet = ItemStack.EMPTY;
		ItemStack chestplate = ItemStack.EMPTY;
		ItemStack leggings = ItemStack.EMPTY;
		ItemStack boots = ItemStack.EMPTY;
		ItemStack main = ItemStack.EMPTY;
		ItemStack off = ItemStack.EMPTY;
		main = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		off = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
		helmet = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY);
		chestplate = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY);
		leggings = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
		boots = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
		if (helmet.getItem() == MysticcraftModItems.WATER_DRAGON_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 1;
		}
		if (chestplate.getItem() == MysticcraftModItems.WATER_DRAGON_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 1;
		}
		if (leggings.getItem() == MysticcraftModItems.WATER_DRAGON_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 1;
		}
		if (boots.getItem() == MysticcraftModItems.WATER_DRAGON_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 1;
		}
		if (helmet.getItem() == MysticcraftModItems.ICE_DRAGON_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 3;
		}
		if (chestplate.getItem() == MysticcraftModItems.ICE_DRAGON_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 3;
		}
		if (leggings.getItem() == MysticcraftModItems.ICE_DRAGON_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 3;
		}
		if (boots.getItem() == MysticcraftModItems.ICE_DRAGON_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 3;
		}
		if (helmet.getItem() == MysticcraftModItems.FIRE_DRAGON_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 5;
		}
		if (chestplate.getItem() == MysticcraftModItems.FIRE_DRAGON_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 5;
		}
		if (leggings.getItem() == MysticcraftModItems.FIRE_DRAGON_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 5;
		}
		if (boots.getItem() == MysticcraftModItems.FIRE_DRAGON_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 5;
		}
		if (helmet.getItem() == MysticcraftModItems.REFINED_IRON_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 10;
		}
		if (chestplate.getItem() == MysticcraftModItems.REFINED_IRON_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 10;
		}
		if (leggings.getItem() == MysticcraftModItems.REFINED_IRON_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 10;
		}
		if (boots.getItem() == MysticcraftModItems.REFINED_IRON_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 10;
		}
		if (helmet.getItem() == MysticcraftModItems.REFINED_DIAMOND_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 20;
		}
		if (chestplate.getItem() == MysticcraftModItems.REFINED_DIAMOND_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 20;
		}
		if (leggings.getItem() == MysticcraftModItems.REFINED_DIAMOND_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 20;
		}
		if (boots.getItem() == MysticcraftModItems.REFINED_DIAMOND_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 20;
		}
		if (main.getItem() == MysticcraftModItems.ASPECTOFTHE_DRAGONS.get()) {
			IntAdd = IntAdd + 20;
		}
		{
			double _setval = IntAdd + (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).BaseStrength;
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.Strenght = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}