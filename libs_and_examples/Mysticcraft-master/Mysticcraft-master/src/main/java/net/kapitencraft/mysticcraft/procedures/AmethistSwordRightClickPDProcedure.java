package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.BlockPos;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class AmethistSwordRightClickPDProcedure {
	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		if (event.getHand() != event.getPlayer().getUsedItemHand())
			return;
		execute(event, event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPlayer());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack main = ItemStack.EMPTY;
		main = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		if (main.getItem() == MysticcraftModItems.AMETHIST_SWORD.get()) {
			if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main) > 0) {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 100
								- 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)) {
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true)
								.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
						for (Entity entityiterator : _entfound) {
							if (!(entityiterator == entity)) {
								if (world instanceof ServerLevel _level) {
									LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
									entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos(x, y, z)));
									entityToSpawn.setVisualOnly(true);
									_level.addFreshEntity(entityToSpawn);
								}
								entityiterator.hurt(DamageSource.GENERIC, 3);
							}
						}
					}
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
								- (100 - 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main));
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent((" Used Zap " + (("("
								+ (100 - 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)))
								+ ")"))), (false));
				}
			} else {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 100) {
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true)
								.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
						for (Entity entityiterator : _entfound) {
							if (!(entityiterator == entity)) {
								if (world instanceof ServerLevel _level) {
									LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
									entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos(x, y, z)));
									entityToSpawn.setVisualOnly(true);
									_level.addFreshEntity(entityToSpawn);
								}
								entityiterator.hurt(DamageSource.GENERIC, 3);
							}
						}
					}
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 100;
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent(" Used Zap (100) "), (false));
				}
			}
		}
		if (main.getItem() == MysticcraftModItems.WANDOF_HEALING.get()) {
			if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main) > 0) {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 60
								- 6 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
								- (60 - 6 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main));
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 2, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent((" Used Small Heal "
								+ (("(" + (60 - 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)))
										+ " Mana)"))),
								(false));
				}
			} else {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 60) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 60;
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 2, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent(" Used Small Heal (60 Mana) "), (false));
				}
			}
		}
		if (main.getItem() == MysticcraftModItems.WANDOF_MENDING.get()) {
			if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main) > 0) {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 90
								- 9 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
								- (90 - 9 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main));
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 3, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent((" Used Medium Heal "
								+ (("(" + (90 - 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)))
										+ " Mana)"))),
								(false));
				}
			} else {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 90) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 90;
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 3, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent(" Used Medium Heal (90 Mana) "), (false));
				}
			}
		}
		if (main.getItem() == MysticcraftModItems.WANDOF_REGENERATION.get()) {
			if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main) > 0) {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 210
								- 21 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
								- (210 - 21 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main));
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 5, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent((" Used Big Heal " + (("("
								+ (210 - 10 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), main)))
								+ " Mana)"))), (false));
				}
			} else {
				if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 210) {
					{
						double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 210;
						entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Mana = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 5, (false), (false)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(new TextComponent(" Used Big Heal (210 Mana) "), (false));
				}
			}
		}
	}
}
