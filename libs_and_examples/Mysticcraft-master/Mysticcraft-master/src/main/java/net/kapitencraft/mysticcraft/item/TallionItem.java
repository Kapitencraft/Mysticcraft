
package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;





public class TallionItem extends Item {
	public TallionItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(2000).rarity(Rarity.EPIC));
	}
	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.EAT;
	}
	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}
	@Override
	public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
		if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
			Player player = (Player) entityLiving;
			boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemstack) > 0;
			int elfmasterench = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ELVISH_MASTERY.get(), itemstack);
			ItemStack ammo = ProjectileWeaponItem.getHeldProjectile(entity, e -> e.getItem() == Items.ARROW);
			double x = player.getX(); double y = player.getY(); double z = player.getZ();
			if (true) {

				if (ammo == ItemStack.EMPTY) {
					for (int i = 0; i < entity.getInventory().items.size(); i++) {
						ItemStack teststack = entity.getInventory().items.get(i);
						if (teststack != null && teststack.getItem() == Items.ARROW) {
							ammo = teststack;
							break;
						}
					}
				}
				if (entity.getAbilities().instabuild || ammo != ItemStack.EMPTY) {
					double damage = 8;
					float power = 3f;
					int knockback = 2;
					int enchpower = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemstack);
					int enchpunch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemstack);
					int enchflame = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemstack);
					damage = damage + (double) enchpower * 0.5 + 0.5;
					knockback = knockback + enchpunch;
					ArrowItem arritem = (ArrowItem)(ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
					AbstractArrow entityarrow = arritem.createArrow(world, ammo, player);
					if (enchflame > 0) {
						entityarrow.setSecondsOnFire(100);
					}
					entityarrow.setCritArrow(true);
					itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
					if (entity.getAbilities().instabuild) {
						entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
					} else {
						if (new ItemStack(Items.ARROW).isDamageableItem()) {
							if (ammo.hurt(1, world.getRandom(), entity)) {
								ammo.shrink(1);
								ammo.setDamageValue(0);
								if (ammo.isEmpty())
									entity.getInventory().removeItem(ammo);
							}
						} else {
							ammo.shrink(1);
							if (ammo.isEmpty())
								entity.getInventory().removeItem(ammo);
						}
					}
					entityarrow.setBaseDamage(damage);
					entityarrow.setKnockback(knockback);
					entityarrow.shootFromRotation(player, player.getXRot() + 12f, player.getYRot(), 0.0f, 12, 1.0f);
					AbstractArrow entityarrow2 = entityarrow;
					entityarrow2.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 12, 1.0f);
					AbstractArrow entityarrow3 = entityarrow;
					entityarrow3.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 12, 1.0f);
					world.addFreshEntity(entityarrow);
					world.addFreshEntity(entityarrow2);
					world.addFreshEntity(entityarrow3);
					world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
               }
			}
		}
	}
}
