package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.BlockPos;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ManaUsedProcedure {
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
		ItemStack rclickeditem = ItemStack.EMPTY;
		double manause = 0;
		rclickeditem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		manause = rclickeditem.getOrCreateTag().getDouble("manause")
				* (1 - EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ULTIMATE_WISE.get(), rclickeditem) * 0.1);
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= manause) {
			{
				double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - manause;
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Mana = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent(("\u00A74" + manause * (-1))), (true));
		} else {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos(x, y, z),
							ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.scream")), SoundSource.HOSTILE, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.scream")),
							SoundSource.HOSTILE, 1, 1, false);
				}
			}
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("\u00A74NOT ENOUGH MANA"), (true));
		}
	}
}
