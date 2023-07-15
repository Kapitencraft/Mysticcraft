package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class YeetPDProcedure {
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
		ItemStack main = ItemStack.EMPTY;
		main = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET.get(), main) > 0) {
			entity.setDeltaMovement(new Vec3(0, (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.YEET.get(), main)), 0));
		}
	}
}
