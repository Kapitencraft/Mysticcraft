package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

@Mod.EventBusSubscriber
public class EntityDeathProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDropsEvent event) {
		if (event != null) {
			if (event.getSource() instanceof EntityDamageSource) {
				EntityDamageSource source = (EntityDamageSource)event.getSource();
				Entity attacker = source.getEntity();
				if (attacker instanceof LivingEntity) {
					LivingEntity livingattacker = (LivingEntity)attacker;
					if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.TELEKINESIS.get(), livingattacker.getMainHandItem()) > 0) {
						for (ItemEntity ent : event.getDrops()) {
							livingattacker.get
						}
					}
				}
			}
		}
	}
}
