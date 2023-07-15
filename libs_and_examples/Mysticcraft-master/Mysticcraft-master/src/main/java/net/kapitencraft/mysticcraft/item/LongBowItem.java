
package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import net.minecraftforge.event.ForgeEventFactory;

import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import java.util.List;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Rarity;

public class LongBowItem extends Item {
	private static final int MAX_DRAW_DURATION = 40;
	public LongBowItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1000).rarity(Rarity.RARE));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		entity.startUsingItem(hand);
		return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("Takes a while to load but makes much damage"));
		list.add(new TextComponent(""));
		list.add(new TextComponent("Do you know how hard modelling this is?"));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public int getSpeedMul() {
		return this.MAX_DRAW_DURATION;
	}

	@Override
	public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof ServerPlayer entity) {
			ServerPlayer player = (ServerPlayer) entityLiving;
			boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemstack) > 0;
			int elfmasterench = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ELVISH_MASTERY.get(), itemstack);
			ItemStack ammo = ProjectileWeaponItem.getHeldProjectile(entity, e -> e.getItem() == Items.ARROW);
			int usedur = this.getUseDuration(itemstack) - timeLeft;
			usedur = ForgeEventFactory.onArrowLoose(itemstack, world, player, usedur, !ammo.isEmpty() || flag);
			float powermul = getPowerForTimeModded(usedur, elfmasterench);
			if (powermul < 0.1f) {
				player.displayClientMessage(new TextComponent(String.valueOf(powermul)), (false));
				return;
			}
			double x = player.getX(); double y = player.getY(); double z = player.getZ();
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
					double damage = 5;
					float power = 1f;
					int knockback = 5;
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
					if (powermul == 1f) {
						entityarrow.setCritArrow(true);
					}
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
					entityarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 12, 1.0f);
					entityarrow.setBaseDamage(damage);
					entityarrow.setKnockback(knockback);
					world.addFreshEntity(entityarrow);
					world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + powermul * 0.5F);
               }
			}
		}
	}
	public static float getPowerForTimeModded(int p_40662_, int masteryLvL) {
      float f = (float)p_40662_ / 40;
      f = (f * f + f * 2.0F) / 3.0F;
      f = (float)(f * (1- (masteryLvL * 0.1)));
      if (f > 1.0F) {
         f = 1.0F;
      }
      return f;
   }
}
