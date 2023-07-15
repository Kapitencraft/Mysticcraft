package net.kapitencraft.mysticcraft.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import net.kapitencraft.mysticcraft.MysticcraftMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MysticcraftModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		MysticcraftMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new,
				PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getPlayer().level.isClientSide())
				((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
						.syncPlayerVariables(event.getPlayer());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getPlayer().level.isClientSide())
				((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
						.syncPlayerVariables(event.getPlayer());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getPlayer().level.isClientSide())
				((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
						.syncPlayerVariables(event.getPlayer());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PlayerVariables()));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PlayerVariables()));
			if (!event.isWasDeath()) {
				clone.Mana = original.Mana;
				clone.MaxMana = original.MaxMana;
				clone.Intelligence = original.Intelligence;
				clone.BaseInt = original.BaseInt;
				clone.Strenght = original.Strenght;
				clone.BaseStrength = original.BaseStrength;
				clone.yvec = original.yvec;
				clone.zvec = original.zvec;
				clone.xvec = original.xvec;
				clone.CritDamage = original.CritDamage;
				clone.manaused = original.manaused;
				clone.MagicFind = original.MagicFind;
			}
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("mysticcraft", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public double Mana = 100.0;
		public double MaxMana = 100.0;
		public double Intelligence = 0.0;
		public double BaseInt = 0;
		public double Strenght = 0;
		public double BaseStrength = 0;
		public double yvec = 0;
		public double zvec = 0;
		public double xvec = 0;
		public double CritDamage = 0;
		public String manaused = "\"\"";
		public double MagicFind = 0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				MysticcraftMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("Mana", Mana);
			nbt.putDouble("MaxMana", MaxMana);
			nbt.putDouble("Intelligence", Intelligence);
			nbt.putDouble("BaseInt", BaseInt);
			nbt.putDouble("Strenght", Strenght);
			nbt.putDouble("BaseStrength", BaseStrength);
			nbt.putDouble("yvec", yvec);
			nbt.putDouble("zvec", zvec);
			nbt.putDouble("xvec", xvec);
			nbt.putDouble("CritDamage", CritDamage);
			nbt.putString("manaused", manaused);
			nbt.putDouble("MagicFind", MagicFind);
			return nbt;
		}

		public void readNBT(Tag Tag) {
			CompoundTag nbt = (CompoundTag) Tag;
			Mana = nbt.getDouble("Mana");
			MaxMana = nbt.getDouble("MaxMana");
			Intelligence = nbt.getDouble("Intelligence");
			BaseInt = nbt.getDouble("BaseInt");
			Strenght = nbt.getDouble("Strenght");
			BaseStrength = nbt.getDouble("BaseStrength");
			yvec = nbt.getDouble("yvec");
			zvec = nbt.getDouble("zvec");
			xvec = nbt.getDouble("xvec");
			CritDamage = nbt.getDouble("CritDamage");
			manaused = nbt.getString("manaused");
			MagicFind = nbt.getDouble("MagicFind");
		}
	}

	public static class PlayerVariablesSyncMessage {
		public PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables()));
					variables.Mana = message.data.Mana;
					variables.MaxMana = message.data.MaxMana;
					variables.Intelligence = message.data.Intelligence;
					variables.BaseInt = message.data.BaseInt;
					variables.Strenght = message.data.Strenght;
					variables.BaseStrength = message.data.BaseStrength;
					variables.yvec = message.data.yvec;
					variables.zvec = message.data.zvec;
					variables.xvec = message.data.xvec;
					variables.CritDamage = message.data.CritDamage;
					variables.manaused = message.data.manaused;
					variables.MagicFind = message.data.MagicFind;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
