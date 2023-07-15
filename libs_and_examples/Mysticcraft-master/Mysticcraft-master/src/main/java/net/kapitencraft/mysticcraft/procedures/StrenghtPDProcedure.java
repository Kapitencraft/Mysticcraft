package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;
import net.kapitencraft.mysticcraft.init.MysticcraftModMobEffects;
import net.kapitencraft.mysticcraft.MysticcraftMod;


import net.kapitencraft.mysticcraft.enchantment.FirstStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.DoubleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.TripleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.QuadripleStrikeEnchantment;
import net.kapitencraft.mysticcraft.enchantment.LowFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.MediumFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.StrongFocusEnchantment;
import net.kapitencraft.mysticcraft.enchantment.UltimateFocusEnchantment;

import javax.annotation.Nullable;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraft.world.item.enchantment.Enchantment;
import com.google.common.net.MediaType;
import com.sun.jna.platform.win32.COM.IStream;
import java.util.Random;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraft.client.Minecraft;




@Mod.EventBusSubscriber
public class StrenghtPDProcedure {
	@SubscribeEvent
	public static void onEntityDamaged(LivingDamageEvent event) {
		if (event != null) {
			if (event.getSource() instanceof EntityDamageSource) {
				EntityDamageSource source = (EntityDamageSource)event.getSource();
				Entity entityattacker = source.getEntity();
				LivingEntity attacker = null;
				LivingEntity attacked = (LivingEntity)event.getEntity();
				if (entityattacker instanceof LivingEntity) {
					attacker = (LivingEntity)entityattacker;
				} else {
					return;
				}
				if (attacker.hasEffect(MysticcraftModMobEffects.PEACE.get())) {
					event.setAmount(0);
					return;
				}
				ItemStack mainhanditem = attacker.getMainHandItem();
				Level world = attacker.level;
				float dmgmuladd = getstrikeenchmul(mainhanditem,attacker) + getfocusenchmul(mainhanditem,attacker);
				float strenth = (float)attacker.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new MysticcraftModVariables.PlayerVariables()).Strenght;
				event.setAmount((float)((event.getAmount()*(1+strenth/100)) * (1 + dmgmuladd)));
			}
			ArmorStand dmgindc = new ArmorStand(world, attacked.getX() + (Math.random() - 0.5), attacked.getY(), attacked.getZ() + (Math.random() - 0.5));
			CompoundTag dmgindctag = dmgindc.getPersistentData();
			dmgindc.setCustomName(Component.Serializer.fromJson(String.valueOf(event.getAmount())));
			dmgindc.setCustomNameVisible(true);
			dmgindc.setNoGravity(true); dmgindc.setInvisible(true); dmgindc.setInvulnerable(true);
			dmgindc.setBoundingBox(new AABB(0,0,0,0,0,0));
			world.addFreshEntity(dmgindc);
			new Object() {
				private int ticks = 0;
				private float waitTicks;
				private LevelAccessor world;
				public void start(LevelAccessor world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}
			
				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}
				private void run() {
					dmgindc.kill();
				}
			}.start(world, 20);
		}
	}
	private static float getstrikeenchmul(ItemStack item, LivingEntity ent) {
		MysticcraftMod.LOGGER.info("scanning for strike Enchantment");
		int firststrklvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.FIRST_STRIKE.get(),item);
		int dblestrlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.DOUBLE_STRIKE.get(),item);
		int trplstrlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.TRIPLE_STRIKE.get(),item);
		int qudrpstrlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.QUADRIPLE_STRIKE.get(),item);
		CompoundTag persisdata = ent.getPersistentData();
		if (persisdata.getDouble("muladd") == 0.0D && persisdata.getString("prehitmul").equals("")) {
			if (firststrklvl > 0) {
				persisdata.putDouble("muladd", 1);
				persisdata.putString("prehitmul", "firststrike");
			} else if (dblestrlvl > 0) {
				persisdata.putDouble("muladd", 2);
				persisdata.putString("prehitmul", "doublestrike");
			} else if (trplstrlvl > 0) {
				persisdata.putDouble("muladd", 3);
				persisdata.putString("prehitmul", "triplestrike");
			} else if (qudrpstrlvl > 0) {
				persisdata.putDouble("muladd", 4);	
				persisdata.putString("prehitmul", "quadriple_strike");
			}
		}
		float dmgmuladd = 0f;
		if (persisdata.getDouble("muladd") != 0) {
			String prehitmul = persisdata.getString("prehitmul");
			if (prehitmul.equals("firststrike")) {
				dmgmuladd = (float)(firststrklvl * 0.25);
			} else if (prehitmul.equals("doublestrike")) {
				dmgmuladd = (float)(dblestrlvl * 0.3);
			} else if (prehitmul.equals("triplestrike")) {
				dmgmuladd = (float)(trplstrlvl * 0.15);
			} else if (prehitmul.equals("quadriple_strike")) {
				dmgmuladd = (float)(qudrpstrlvl * 0.2);
			}
			persisdata.putDouble("muladd", persisdata.getDouble("muladd") - 1D);
		}
		return dmgmuladd;
	}

	private static float getfocusenchmul(ItemStack item, LivingEntity ent) {
		MysticcraftMod.LOGGER.info("scanning for focus Enchantment");
		int lolvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.LOW_FOCUS.get(),item);
		int medlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.MEDIUM_FOCUS.get(),item);
		int strlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.STRONG_FOCUS.get(),item);
		int ultlvl = EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_FOCUS.get(),item);
		CompoundTag persisdata = ent.getPersistentData();
		if (persisdata.getString("fcsprehitmul").equals("")) {
			if (lolvl > 0) {
				persisdata.putString("fcsprehitmul", "lo");
			} else if (medlvl > 0) {
				persisdata.putString("fcsprehitmul", "med");
			} else if (strlvl > 0) {
				persisdata.putString("fcsprehitmul", "str");
			} else if (ultlvl > 0) {
				persisdata.putString("fcsprehitmul", "ult");
			}
		}
		float dmgmuladd = 0f;
		String prehitmul = persisdata.getString("fcsprehitmul");
		if (persisdata.getDouble("muladd") < 10) {
			if (prehitmul.equals("lo")) {
				dmgmuladd = (float)(lolvl * -0.25);
			} else if (prehitmul.equals("med")) {
				dmgmuladd = (float)(medlvl * -0.3);
			} else if (prehitmul.equals("str")) {
				dmgmuladd = (float)(strlvl * -0.35);
			} else if (prehitmul.equals("ult")) {
				dmgmuladd = (float)(ultlvl * -0.4);
			}
			persisdata.putDouble("muladd", persisdata.getDouble("muladd") + 1D);
		} else {
			if (prehitmul.equals("lo")) {
				dmgmuladd = (float)(lolvl * 2.5);
			} else if (prehitmul.equals("med")) {
				dmgmuladd = (float)(medlvl * 3);
			} else if (prehitmul.equals("str")) {
				dmgmuladd = (float)(strlvl * 3.5);
			} else if (prehitmul.equals("ult")) {
				dmgmuladd = (float)(ultlvl * 4);
			}
			persisdata.putDouble("muladd", 0);
		}
		return dmgmuladd; 
	}
	 public double enchcldown(Enchantment ench) {
	 	if (ench instanceof LowFocusEnchantment) {
	 		return 12;
	 	} else if (ench instanceof MediumFocusEnchantment) {
	 		return 11;
	 	} else if (ench instanceof StrongFocusEnchantment) {
	 		return 9;
	 	} else if (ench instanceof UltimateFocusEnchantment) {
	 		return 7;
	 	} else {
	 		return 0;
	 	}
	}
}
