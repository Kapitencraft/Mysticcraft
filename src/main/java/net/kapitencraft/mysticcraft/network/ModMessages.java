package net.kapitencraft.mysticcraft.network;

import net.kapitencraft.mysticcraft.network.packets.C2S.*;
import net.kapitencraft.mysticcraft.network.packets.S2C.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class ModMessages {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ToggleUpgradeVisibilityPacket.TYPE, ToggleUpgradeVisibilityPacket.STREAM_CODEC, ToggleUpgradeVisibilityPacket::handle);
        registrar.playToServer(UseShortBowPacket.TYPE, UseShortBowPacket.STREAM_CODEC, UseShortBowPacket::handle);
        registrar.playToServer(UpgradeItemPacket.TYPE, UpgradeItemPacket.STREAM_CODEC, UpgradeItemPacket::handle);
        registrar.playToClient(SyncManaDistributionNetworksPacket.TYPE, SyncManaDistributionNetworksPacket.STREAM_CODEC, SyncManaDistributionNetworksPacket::handle);
        registrar.playToServer(ReforgeItemPacket.TYPE, ReforgeItemPacket.STREAM_CODEC, ReforgeItemPacket::handle);
        registrar.playToClient(SwingPacket.TYPE, SwingPacket.STREAM_CODEC, SwingPacket::handle);
        registrar.playToClient(RemoveManaDistributionNetworkElementPacket.TYPE, RemoveManaDistributionNetworkElementPacket.STREAM_CODEC, RemoveManaDistributionNetworkElementPacket::handle);
        registrar.playToClient(HammerAbortBreakPacket.TYPE, HammerAbortBreakPacket.STREAM_CODEC, HammerAbortBreakPacket::handle);
        registrar.playToClient(UpdateClassProgressionPacket.TYPE, UpdateClassProgressionPacket.STREAM_CODEC, UpdateClassProgressionPacket::handle);
        registrar.playToServer(SelectSpellSlotPacket.TYPE, SelectSpellSlotPacket.STREAM_CODEC, SelectSpellSlotPacket::handle);
        registrar.playToClient(BreathParticlesPacket.TYPE, BreathParticlesPacket.STREAM_CODEC, BreathParticlesPacket::handle);
        registrar.playToServer(SetTargetPriorityPacket.TYPE, SetTargetPriorityPacket.STREAM_CODEC, SetTargetPriorityPacket::handle);
        registrar.playToClient(SkillXpChangedPacket.TYPE, SkillXpChangedPacket.STREAM_CODEC, SkillXpChangedPacket::handle);
        registrar.playToServer(AssignTraitTokenPacket.TYPE, AssignTraitTokenPacket.STREAM_CODEC, AssignTraitTokenPacket::handle);
    }
}