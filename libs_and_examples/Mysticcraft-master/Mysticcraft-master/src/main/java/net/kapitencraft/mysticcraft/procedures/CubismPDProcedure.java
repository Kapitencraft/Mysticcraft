package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class CubismPDProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(),
					event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack main = ItemStack.EMPTY;
		main = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		if (entity instanceof MagmaCube && EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.CUBISM.get(), main) > 0) {
			entity.hurt(DamageSource.GENERIC, EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.CUBISM.get(), main));
		}
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Strenght > 0) {
			entity.hurt(DamageSource.GENERIC, (float) (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).Strenght);
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.TELEKINESIS.get(), main) > 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2 / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if (entity instanceof ItemEntity) {
						if (entity instanceof Player _player) {
							ItemStack _setstack = (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY);
							_setstack.setCount(1);
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						if (!entityiterator.level.isClientSide())
							entityiterator.discard();
					}
				}
			}
		}
	}
}
