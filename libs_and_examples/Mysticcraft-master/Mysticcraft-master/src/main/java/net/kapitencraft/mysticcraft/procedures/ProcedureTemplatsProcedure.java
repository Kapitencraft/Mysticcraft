package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.Minecraft;

import net.kapitencraft.mysticcraft.init.MysticcraftModMobEffects;
import net.kapitencraft.mysticcraft.MysticcraftMod;

import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.List;
import java.util.Comparator;

public class ProcedureTemplatsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		double type = 0;
		if (entity instanceof Player _player && !_player.level.isClientSide())
			_player.displayClientMessage(new TextComponent("Message"), (false));
		type = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MysticcraftModMobEffects.PEACE.get())
				? _livEnt.getEffect(MysticcraftModMobEffects.PEACE.get()).getAmplifier()
				: 0;
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) Math.random());
		{
			AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
			entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> _iitemhandlerref.set(capability));
			if (_iitemhandlerref.get() != null) {
				for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
					ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx).copy();
					if (world.isClientSide())
						Minecraft.getInstance().gameRenderer.displayItemActivation(itemstackiterator);
				}
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream()
					.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null, x, y, z, 4, Explosion.BlockInteraction.BREAK);
			}
		}
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
				YeetThornsPDProcedure.execute(entity, sourceentity);
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, 20);
		MysticcraftMod.LOGGER.info("Message");
		if (itemstack.is(ItemTags.create(new ResourceLocation("minecraft:logs")))) {
			(itemstack).grow(1);
		}
	}
}
