package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.kapitencraft.mysticcraft.init.MysticcraftModItems;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HealthChangeProcedure {
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
		ItemStack helmet = ItemStack.EMPTY;
		ItemStack chestplate = ItemStack.EMPTY;
		ItemStack leggings = ItemStack.EMPTY;
		ItemStack boots = ItemStack.EMPTY;
		ItemStack main = ItemStack.EMPTY;
		ItemStack off = ItemStack.EMPTY;
		double IntAdd = 0;
		double health = 0;
		main = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		off = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
		helmet = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY);
		chestplate = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY);
		leggings = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
		boots = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
		IntAdd = 0;
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), helmet) > 0) {
			IntAdd = IntAdd + EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), helmet);
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), chestplate) > 0) {
			IntAdd = IntAdd + EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), chestplate);
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), leggings) > 0) {
			IntAdd = IntAdd + EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), leggings);
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), boots) > 0) {
			IntAdd = IntAdd + EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.GROTH.get(), boots);
		}
		if (helmet.getItem() == MysticcraftModItems.AMETHIST_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 5;
		}
		if (chestplate.getItem() == MysticcraftModItems.AMETHIST_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 5;
		}
		if (leggings.getItem() == MysticcraftModItems.AMETHIST_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 5;
		}
		if (boots.getItem() == MysticcraftModItems.AMETHIST_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 5;
		}
		if (helmet.getItem() == MysticcraftModItems.RUBY_ARMOR_HELMET.get()) {
			IntAdd = IntAdd + 1;
		}
		if (chestplate.getItem() == MysticcraftModItems.RUBY_ARMOR_CHESTPLATE.get()) {
			IntAdd = IntAdd + 2;
		}
		if (leggings.getItem() == MysticcraftModItems.RUBY_ARMOR_LEGGINGS.get()) {
			IntAdd = IntAdd + 2;
		}
		if (boots.getItem() == MysticcraftModItems.RUBY_ARMOR_BOOTS.get()) {
			IntAdd = IntAdd + 1;
		}
		if (main.getItem() == MysticcraftModItems.ASPECTOFTHE_DRAGONS.get()) {
			IntAdd = IntAdd + 2;
		}
		if (IntAdd > 0) {
			health = entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.HEALTH_BOOST);
			if (entity instanceof LivingEntity _entity)
				_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 40, (int) (IntAdd / 2), (false), (false)));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) health);
		}
	}
}
